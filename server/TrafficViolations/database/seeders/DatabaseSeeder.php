<?php

namespace Database\Seeders;

use App\Models\Admin;
use App\Models\Car;
use App\Models\Category;
use App\Models\User;
use App\Models\Violation;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\Hash;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
        Admin::create(['id' => 9238471, 'username' => 'MajdSh', 'password' => bcrypt('qwert123456')]);

        $user = User::create(['username' => 'Yassen', 'password' => bcrypt('qwert123456'), 'phone_number' => '932842344', 'slug' => 'yassen', 'active' => 0]);
        $user2 = User::create(['username' => 'Jhin', 'password' => bcrypt('qwert123456'), 'phone_number' => '932342374', 'slug' => 'jhin', 'active' => 0]);
        $user3 = User::create(['username' => 'Mhmd', 'password' => bcrypt('qwert123456'), 'phone_number' => '932842374', 'slug' => 'mhmd', 'active' => 0]);

        Car::create(['model' => 'i3', 'brand' => 'BMW', 'code' => '893478', 'user_id' => $user->id]);
        Car::create(['model' => 'mx', 'brand' => 'Ford', 'code' => '893433', 'user_id' => $user2->id]);
        Car::create(['model' => 'x2', 'brand' => 'Oudi', 'code' => '893422', 'user_id' => $user3->id]);

        $category = Category::create(['name' => 'Hit and run', 'slug' => 'hit-and-run', 'price' => 50]);
        $category2 = Category::create(['name' => 'Reckless driving', 'slug' => 'reckless-driving', 'price' => 20]);
        $category3 = Category::create(['name' => 'Vehicular manslaughter', 'slug' => 'vehicular-manslaughter', 'price' => 40]);

        Violation::create(['location' => 'Dread Street', 'user_id' => $user->id, 'category_id' => $category->id]);
        Violation::create(['location' => 'Champer of sins', 'user_id' => $user->id, 'category_id' => $category2->id]);

        Violation::create(['location' => 'Solaris temple', 'user_id' => $user2->id, 'category_id' => $category2->id]);
        Violation::create(['location' => 'Sarn', 'user_id' => $user2->id, 'category_id' => $category3->id]);

        Violation::create(['location' => 'Lioneyes Watch', 'user_id' => $user3->id, 'category_id' => $category->id]);
        Violation::create(['location' => 'Sand Beach', 'user_id' => $user3->id, 'category_id' => $category2->id]);
        Violation::create(['location' => 'Westlands', 'user_id' => $user3->id, 'category_id' => $category3->id]);
    }
}
