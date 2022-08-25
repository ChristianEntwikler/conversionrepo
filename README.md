# conversionrepo
Service for converting from Metric to Imperial and vice versa <br/>
There is no extra installation <br/>

Build the application or pick the already built application from <b>/target</b> folder <br/>
Deploy with Docker  <br/>
Try the sample APIs below once service is started <br/>
Below are the sample APIs <br/> <br/>
{IP} - Where you specify the server IP or use locahost if you are testing from the server that the service was deployed <br/>
{port} - This is the port docker is using to run the application <br/>  <br/>
1. Measurment Types  <br/>
URL: {IP}:{port}/api/list/measurement  <br/>
REQUEST TYPE: GET  <br/>  <br/>
<code>
[
    {
        "id": 1,
        "measureFromName": "Centimeters",
        "converFromType": "Metric",
        "measureToName": "Inches",
        "converToType": "Imperial",
        "unitValue": "0.39370",
        "dateCreated": "2022-08-25T10:36:28.630+00:00"
    },
    {
        "id": 2,
        "measureFromName": "Kilometers",
        "converFromType": "Metric",
        "measureToName": "Miles",
        "converToType": "Imperial",
        "unitValue": "0.62137",
        "dateCreated": "2022-08-25T10:36:28.636+00:00"
    },
    {
        "id": 3,
        "measureFromName": "Celsius",
        "converFromType": "Metric",
        "measureToName": "Fahrenheit",
        "converToType": "Imperial",
        "unitValue": "((#VALUE# x 9/5) + 32)",
        "dateCreated": "2022-08-25T10:36:28.637+00:00"
    }
]
</code>
 <br/>  <br/>

2. Conversion Types  <br/>
URL: {IP}:{port}/api/list/conversionTypes  <br/>
REQUEST TYPE: GET  <br/>
 <br/>
<code>
[
    {
        "id": 2,
        "conversionType": "Imperial"
    },
    {
        "id": 1,
        "conversionType": "Metric"
    }
]
</code>
 <br/> <br/>

3. Add Measurement Type  <br/>
URL: {IP}:{port}/api/measurementunit/add  <br/>
REQUEST TYPE: POST  <br/>
<code>
REQUEST PAYLOAD
{
        "measureFromName": "Grams",
        "converFromType": "Metric",
        "measureToName": "Ounces",
        "converToType": "Imperial",
        "unitValue": "0.035"
}

RESPONSE
{
    "statusCode": "00",
    "statusMessage": "Measurement Unit Added Successfully"
}

</code>

 <br/>  <br/>

4. Convert  <br/>
URL: {IP}:{port}/api/measurement/convert  <br/>
REQUEST TYPE: POST  <br/>
 <br/>
<code>
REQUEST PAYLOAD
{
        "measureFromName": "Grams",
        "converFromType": "Metric",
        "measureToName": "Ounces",
        "converToType": "Imperial",
        "unitValue": "1"
}

RESPONSE
{
    "statusCode": "00",
    "statusMessage": "Successful",
    "result": "0.035"
}

</code>
 <br/>
