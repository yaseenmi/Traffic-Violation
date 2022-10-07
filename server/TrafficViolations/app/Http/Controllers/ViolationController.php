<?php

namespace App\Http\Controllers;

use App\Models\Violation;

use Illuminate\Http\Request;

class ViolationController extends Controller
{
    public function index()
    {
        return Violation::all();
    }

    public function showWithCategory()
    {
        return Violation::with('category')->get();
    }

    public function getRelatedCategories($id)
    {
        $violation = Violation::where('id', $id)->first();

        if (!$violation) {
            return response()->json([
                'success' => false,
                'message' => 'violation not found'
            ]);
        }

        return $violation->category;
    }

    public function store(Request $request)
    {
        $request->validate([
            'location' => "required"
        ]);

        return Violation::create($request->all());
    }

    public function update(Request $request, $id)
    {
        $request->validate([
            'location' => "required"
        ]);

        $violation = Violation::find($id);
        $violation->update($request->all());

        return $violation;
    }

    public function destroy($id)
    {
        $violation = Violation::where('id', $id)->first();

        if (!$violation) {
            return response()->json([
                'success' => false,
                'message' => 'violation not found'
            ]);
        }

        $violation->delete();

        return response()->json([
            'success' => true,
            'message' => 'violation paid'
        ]);
    }

    public function show($id)
    {
        return Violation::find($id);
    }

    public function searchByDate($date)
    {
        return Violation::where('date', $date)->get();
    }

    public function getByDate()
    {
        $attributes = request()->validate([
            'date_from' => 'date',
            'date_to' => 'date'
        ]);

        $from = $attributes['date_from'];
        $to = $attributes['date_to'];

        return Violation::whereBetween('created_at', [$from, $to])->get();
    }
}
