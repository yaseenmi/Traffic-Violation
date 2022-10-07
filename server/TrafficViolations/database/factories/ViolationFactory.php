<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;

class ViolationFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        return [
            'car_id' => $this->faker->unique()->randomNumber(2),
            'category_id' =>$this->faker->numberBetween(0,9),
            'location' => $this->faker->address(),
            'date' => $this->faker->dateTimeThisMonth(),
        ];
    }
}
