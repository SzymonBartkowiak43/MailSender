db.createUser(
    {
        user: "root",
        pwd: "IAmUnsecure!",
        roles: [
            {
                role: "readWrite",
                db: "mailSender"
            }
        ]
    }
);
