// MongoDB Playground
// Use Ctrl+Space inside a snippet or a string literal to trigger completions.

// The current database to use.
use("db_land");

// Find a document in a collection.
db.getCollection("states").aggregate([
    {
        $match: {
            region: "Norte"
        }
    },
    {
        $unwind: "$corporations"
    },
    {
        $group: {
            _id: "$name", // Group by the state name field
            name_state: {
                $first: "$name",
            },
            corporations_state: {
                $push: "$corporations"
            },
            total_employees: {
                $sum: "$corporations.employees" // Sum the employees field from each corporation
            }
        }
    }
]);
