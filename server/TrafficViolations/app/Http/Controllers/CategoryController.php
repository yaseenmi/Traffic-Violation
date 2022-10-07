<?php

namespace App\Http\Controllers;

use App\Models\Category;
use Illuminate\Http\Request;

class CategoryController extends Controller
{
    // Returns Categories without violations
    public function index()
    {
        return Category::all();
    }

    // Shows category details
    public function show($id)
    {
        return Category::find($id);
    }

    // Returns categories with violations
    public function showWithViolations()
    {
        return Category::with('violation')->get();
    }

    // Returns related violations of a category
    public function getRelatedViolation($id)
    {
        $category = Category::where('id', $id)->first();

        if (!$category) {
            return response()->json([
                'success' => false,
                'message' => 'Category not found with an id of ' . $id
            ]);
        }

        return $category->violation;
    }

    // Stores a category
    public function store(Request $request)
    {
        $attributes = $request->validate([
            'name' => 'required|string',
            'slug' => 'required|string',
            'price' => 'required'
        ]);

        $category = Category::create($attributes);

        return response()->json([
            'success' => true,
            'message' => 'Category added successfully',
            'data' => $category
        ]);
    }

    // Updates a category
    public function update($id)
    {
        $category = Category::where('id', $id)->first();

        if (!$category) {
            return response()->json([
                'success' => false,
                'message' => 'Category not found with an id of ' . $id
            ]);
        }

        $attributes = request()->validate([
            'name' => $category->exists ? '' : 'required',
            'slug' => $category->exists ? '' : 'required',
            'price' => $category->exists ? '' : 'required'
        ]);

        $category->update($attributes);

        return response()->json([
            'success' => true,
            'message' => 'Category updated successfully',
            'data' => $category
        ]);;
    }

    // Deletes a category
    public function destroy($id)
    {
        $category = Category::where('id', $id)->first();

        if (!$category) {
            return response()->json([
                'success' => false,
                'message' => 'Category not found with an id of ' . $id
            ]);
        }

        $category->delete();

        return response()->json([
            'success' => true,
            'message' => 'Category deleted successfully',
        ]);
    }

    // Search by category name
    public function getByName()
    {
        $attributes = request()->validate([
            'name' => 'required|string'
        ]);

        return Category::where('name', 'like', '%' . $attributes['name'] . '%')->get();
    }
}
