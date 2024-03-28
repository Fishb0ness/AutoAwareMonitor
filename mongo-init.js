db = new Mongo().getDB("userdb");
//db.createCollection("packs", { capped: false });
//db.createCollection("users", { capped: false });

db.createUser(
    {
        user: "dbadmin",
        pwd: "dbpassword",
        roles: [
            {
                role: "readWrite",
                db: "userdb"
            }
        ]
    }
);

/*db.createCollection("vehicleModel");

db.vehicleModel.insertOne( {
        "_id": BinData(3, "tTNvjf0lRCdcBWH9Ouw8og=="),
        "_class": "com.fishb0ness.autoawaremonitor.adapter.output.mongodb.vehicle.VehicleModelMongoOutDTO",
        "brand": "Seat",
        "model": "Alhambra"
        }
);*/
