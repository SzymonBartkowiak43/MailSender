db.createUser({
    user: 'admin',
    pwd: 'admin',
    roles: [
        { role: 'readWrite', db: 'mails' }
    ]
});

db.createCollection('emails');
