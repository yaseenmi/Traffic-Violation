<?php

use App\Http\Controllers\CarController;
use App\Http\Controllers\CategoryController;
use App\Http\Controllers\UserController;
use App\Http\Controllers\ViolationController;
use Illuminate\Support\Facades\Route;

/*
 |--------------------------------------------------
 |  Public Routes
 |--------------------------------------------------
*/

Route::post('/register', [UserController::class, 'register']);
Route::post('/login', [UserController::class, 'login']);

/*
 |--------------------------------------------------
 |  Private Routes (Users and admin)
 |--------------------------------------------------
*/

Route::group(['middleware' => ['auth:sanctum']], function () {

    // User Routes
    Route::get('/logout', [UserController::class, 'logout']);
    Route::get('/users/{slug}', [UserController::class, 'show']);

    // Category Routes
    Route::get('/category', [CategoryController::class, 'index']);

    // Cars Routes
    Route::get('/car/{id}', [CarController::class, 'show']);
    Route::put('/car/{id}', [CarController::class, 'update']);

    //Category Routes
    Route::get('/category', [CategoryController::class, 'index']);
    Route::get('/category/violation', [CategoryController::class, 'showWithViolations']);
    Route::get('/category/{id}', [CategoryController::class, 'show']);
    Route::get('/category/{id}/violation', [CategoryController::class, 'getRelatedViolation']);
    Route::post('/category/search', [CategoryController::class, 'getByName']);

    //violation Routes
    Route::get('/violation', [ViolationController::class, 'index']);
    Route::get('/violation/category', [ViolationController::class, 'showWithCategory']);
    Route::get('/violation/{id}', [ViolationController::class, 'show']);
    Route::get('/violation/{id}/category', [ViolationController::class, 'getRelatedCategories']);
    Route::delete('/violation/{id}', [ViolationController::class, 'destroy']);
    Route::post('/violation/date', [ViolationController::class, 'getByDate']);
});

/*
 |--------------------------------------------------
 |  Private Routes (admin only)
 |--------------------------------------------------
*/

Route::group(['middleware' => ['auth:sanctum', 'admin']], function () {
    // Category Routes
    Route::post('/category', [CategoryController::class, 'store']);
    Route::put('/category/{id}', [CategoryController::class, 'update']);
    Route::delete('/category/{id}', [CategoryController::class, 'destroy']);

    // Violation routes
    Route::post('/violation', [ViolationController::class, 'store']);
    Route::put('/violation/{id}', [ViolationController::class, 'update']);

    // User Routes
    Route::get('/users', [UserController::class, 'index']);
    Route::put('/users/{id}/confirm', [UserController::class, 'confirm']);

    // Car Routes
    Route::get('/car', [CarController::class, 'index']);
});
