var https = require('https');
var moment = require('moment');

var yesterday = function(){
  var dateFormat = 'YYYY-MM-DD';
  return moment().subtract(1,'days').format(dateFormat);
};

module.exports = function(app)
{    
		app.get('/',function(req,res){
        res.render('index.html')
     	});

//to get chennai contents
//placeId of chennai group is 16275
//auth: 'username:password' 

    app.get('/contents',function(req,response){
     	var options = {
       		    hostname: 'thoughtworks.jiveon.com',
              path: '/api/core/v3/search/contents?filter=place(/places/16275)&filter=type(discussion)&filter=after('+ yesterday() +')&filter=search(chennai)&sort=updatedDesc',
         		  method: 'GET',
         		  headers: { 'Content-Type': 'application/json'},
         		  auth: 'username:password'
     	};
       
      var req = https.request(options, function(res){
        console.log('statusCode: ', res.statusCode);
        var content = [];
        res.on('data', function(d) {
          content.push(d);
        });

        res.on('end',function(){
            var body = content.toString();
            console.log(body);
            body = body.replace("\\/g"," ");
            response.json(body);
        });
      });

      req.end();
      
    req.on('error', function(e) {
      console.error(e);
      });
  });
 }

