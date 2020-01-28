db.createUser({user:'system',pwd:'admin',roles:['readWrite',db:'dsc']});
use dsc
db.createCollection("user")
db.user.insert({
"email":"yashwanth.uppu@ojas-it.com",
"password":"Ojas1525",
"phone_number":"488181818",
"fullname":"uppu yashwanth",
"address":"hihinininin",
"country":"ninsin",
"passwordChanged":true,
"role_id":2})
db.createCollection("role")
db.role.insert({
"role_id":1,
"role":"SUPERADMIN"
},
{
"role_id":2,
"role":"COMPANY_ADMIN"
},
{
"role_id":1,
"role":"COMPANY_USER"
},
{
"role_id":1,
"role":"DISTRIBUTOR"
},
)


