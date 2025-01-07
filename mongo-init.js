db.createUser(
    {
        user: "mailSender",
        pwd: "fnwM<Sj^%CDyc@",
        roles: [
            {
                role: "readWrite",
                db: "mailSender"
            }
        ]
    }
);