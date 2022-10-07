<?php

namespace App\Http\Controllers;

use App\Models\Admin;
use App\Models\Car;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Str;


class UserController extends Controller
{
    /*
     * @desc Returns a list of registed users
     * @roles Admin Only can show all users
    */
    public function index()
    {
        return User::with('car')->get();
    }

    public function show($slug)
    {
        return User::where('slug', $slug)->get();
    }

    /*
     * @desc Registers a user
     * @roles User Only can register
    */
    public function register(Request $request)
    {
        // Validate user data from request
        $userAttributes = $request->validate([
            'username' => 'required|string|unique:users,username',
            'password' => 'required|string|min:8|confirmed',
            'phone_number' => 'required',
        ]);

        // Validate Car data from request
        $carAttributes = $request->validate([
            'model' => 'required',
            'brand' => 'required',
            'code' => 'required|unique:cars,code'
        ]);

        // Create a user to be attached to the car table
        $user = User::create(array_merge($userAttributes, [
            'password' => bcrypt($userAttributes['password']),
            'slug' => $this->Slugify($userAttributes['username']),
            'active' => 0
        ]));

        if (!$user) {
            return response([
                "success" => false,
                "message" => "Something went wrong"
            ]);
        }

        // Create a car and attack user id
        Car::create(array_merge($carAttributes, [
            'user_id' => $user->id
        ]));

        // Create user token
        $token = $user->createToken('myapptoken')->plainTextToken;

        return response()->json([
            'success' => true,
            'message' => $user->username . ' has been register successfully',
            'data' => $user,
            'token' => $token
        ]);
    }

    /*
    * @desc Logins a user or an admin
    * @roles Admin and User can login
    */
    public function login(Request $request)
    {
        // Validate request
        $fields = $request->validate([
            'username' => 'required|string',
            'password' => 'required|string|min:8',
        ]);

        // Check username and role
        $auth = null;
        $role = "";

        if ($auth = User::where('username', $fields['username'])->first()) {
            $role = "user";
        } else if ($auth = Admin::where('username', $fields['username'])->first()) {
            $role = "admin";
        }

        // Check passwowrd
        if (!$auth || !Hash::check($fields['password'], $auth->password)) {
            return response([
                'message' => 'Bad creds'
            ], 401);
        }

        // Create user token
        $token = $auth->createToken('myapptoken')->plainTextToken;

        return response([
            'success' => true,
            'message' => $role . ' has logged in',
            'data' => $auth,
            'role' => $role,
            'token' => $token
        ]);
    }

    /*
    * @desc Logouts a User or an Admin
    * @roles Admin and User can logout
    */
    public function logout(Request $request)
    {
        //Delete user token
        $request->user()->tokens()->delete();

        return response()->json([
            'success' => true,
            'message' => 'logged out'
        ]);
    }

    /*
    * @desc Confirms an account
    * @roles Admin
    */
    public function confirm($slug)
    {
        $user = User::where('slug', $slug)->first();

        if (!$user) {
            return response()->json([
                'success' => false,
                'message' => 'User not found wiht an slug of ' . $slug
            ]);
        }

        $user->active = 1;
        $user->save();

        return response()->json([
            'success' => true,
            'message' => 'Account activated'
        ]);
    }

    /*
    |----------------------------------------------
    |   Helper Methods
    |----------------------------------------------
    */

    /* Slugify the username of a user */
    protected function Slugify($username)
    {
        return Str::slug($username);
    }
}
