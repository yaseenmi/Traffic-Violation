<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Violation extends Model
{
    use HasFactory;

    protected $fillable = ['user_id', 'category_id', 'location', 'date'];

    public function category()
    {
        return $this->BelongsTo(Category::class);
    }

    public function driver()
    {
        return $this->BelongsTo(User::class, 'user_id');
    }
}
