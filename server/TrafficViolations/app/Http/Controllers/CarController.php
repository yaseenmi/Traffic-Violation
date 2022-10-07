<?php

namespace App\Http\Controllers;

use App\Models\Car;

class CarController extends Controller
{
    /*
     * @desc Returns cars with drivers informations
     * @roles Admin only can show all the cars
    */
    public function index()
    {
        return Car::with('driver')->get();
    }

    /*
     * @desc Returns cars with drivers informations
     * @roles Admin and user both can show registed user details
    */
    public function show($id)
    {
        return $this->CheckExistRoleAndReturn($id, ['user', 'admin']);
    }

    /*
     * @desc Updates car details
     * @roles User only can update his car informations
    */
    public function update($id)
    {
        $result = $this->CheckExistRoleAndReturn($id, ['user']);

        if (!$result instanceof Car)
            return $result;
        else $car = $result;

        $attributes = $this->validateCar($car);

        $car->update($attributes);

        return response()->json([
            'success' => true,
            'message' => 'Car has been updated successfully',
            'data' => $car
        ]);
    }

    /*
    |----------------------------------------------
    |   Helper Methods
    |----------------------------------------------
    */
    public function validateCar(?Car $car = null)
    {
        $car ??= new Car();

        return request()->validate([
            'id' => $car->exists ? 'unique:cars,id' : 'required|unique:cars,id',
            'brand' => $car->exists ? 'string' : 'required|string',
            'model' => $car->exists ? 'string' : 'required|string',
            'code' => $car->exists ? 'string|unique:cars,code' : 'required|string|unique:cars,code'
        ]);
    }

    /*
    * @desc Function that checks the existation of a car and check passed roles
    */
    protected function CheckExistRoleAndReturn($id, $role)
    {
        $isAdmin = false;

        $car = Car::where('id', $id)->first();

        if (!$car) {
            return response()->json([
                'success' => false,
                'message' => 'Car with an id of ' . $id . ' not found!'
            ]);
        }

        if (in_array('admin', $role)) {
            if (Auth()->user()->username == "MajdSh") {
                $isAdmin = true;
            }
        }

        if (in_array('user', $role)) {
            if ($car->driver->id != Auth()->user()->id && $isAdmin == false) {
                return response()->json([
                    'success' => false,
                    'message' => 'Access Deined!'
                ]);
            }
        }

        return $car;
    }
}
