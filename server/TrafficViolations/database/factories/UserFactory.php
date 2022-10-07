<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;
use Illuminate\Support\Str;

class UserFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        $carIds = [
            '749320', '839201', '393021', '253932',
            '635232', '866530', '102034', '932041',
            '348201', '853921', '324921', '432851',
            '852342', '938531', '457201', '753312',
            '477212', '832012', '984635', '145325',
            '843131', '323432', '993491', '433238',
            '112353', '194921'
        ];

        return [
            'username' => $this->faker->unique()->name(),
            'password' => bcrypt("qwert123456"),
            'phone_number' => $this->faker->phoneNumber(),
            'car_id' => $carIds[rand(0, 25)]
        ];
    }

    /**
     * Indicate that the model's email address should be unverified.
     *
     * @return \Illuminate\Database\Eloquent\Factories\Factory
     */
    public function unverified()
    {
        return $this->state(function (array $attributes) {
            return [
                'email_verified_at' => null,
            ];
        });
    }
}
