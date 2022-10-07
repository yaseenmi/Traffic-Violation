<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Car extends Model
{
    use HasFactory;

    protected $fillable = ['code', 'brand', 'model', 'user_id'];

    public function driver()
    {
        return $this->belongsTo(User::class, 'user_id');
    }
}
