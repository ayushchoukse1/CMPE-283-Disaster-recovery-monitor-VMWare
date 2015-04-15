
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bar Graph</title>
</head>
 <head>
        <!--Load the AJAX API-->
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
  <link href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet" />
 <script type="text/javascript" src="https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization','version':'1.1','packages':['line', 'corechart']}]}"></script>
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        
        <script type="text/javascript">
        google.load('visualization', '1.1', {packages: ['line', 'corechart']});
        google.setOnLoadCallback(drawChart);
       
          
          function drawChart() {

              // Create the data table.
              var data = new google.visualization.DataTable();
              data.addColumn('date', 'Time');
              data.addColumn('number', "Usage");
              data.addRows([
  [ new Date(2015,04,13,13,15), 4199],
  [ new Date(2015,04,13,13,20), 1859],
  [ new Date(2015,04,13,13,25), 1759],
  [ new Date(2015,04,13,13,30), 432],
  [ new Date(2015,04,13,13,35), 199],
  [ new Date(2015,04,13,13,45), 459],
  [ new Date(2015,04,13,13,50), 2619],
  [ new Date(2015,04,13,13,55), 1112],
  [ new Date(2015,04,13,14,00), 4679],
  [ new Date(2015,04,13,14,05), 3232],
  [ new Date(2015,04,13,14,10), 1679],
              ]);
              // Create the data table.
              var data2 = new google.visualization.DataTable();
              data2.addColumn('date', 'Time');
              data2.addColumn('number', "Usage");
              data2.addRows([
  [ new Date(2015,04,13,13,15), -1],
  [ new Date(2015,04,13,13,20), 5496],
  [ new Date(2015,04,13,13,25), 1959],
  [ new Date(2015,04,13,13,30), 1481],
  [ new Date(2015,04,13,13,35), 1237],
  [ new Date(2015,04,13,13,45), 9699],
  [ new Date(2015,04,13,13,50), 1578],
  [ new Date(2015,04,13,13,55), 3130],
  [ new Date(2015,04,13,14,00), 4111],
  [ new Date(2015,04,13,14,05), 2130],
  [ new Date(2015,04,13,14,10), 2303],

              ]);

          

            // Set chart options
            var options = {'title':'Average Memory Usage (%) of Virtual Machine',
                           'width':900,
                           'height':500,
                           'series': {
                        	      // Gives each series an axis name that matches the Y-axis below.
                        	      0: {axis: 'Temps'},
                        	      1: {axis: 'Daylight'}
                        	    },
                        	    'axes': {
                        	      // Adds labels to each axis; they don't have to match the axis names.
                        	      y: {
                        	        Temps: {label: 'Temps (Celsius)'},
                        	        Daylight: {label: 'Daylight'}
                        	      }
                        	    }};
            // Set chart options
            var options2 = {'title':'Average CPU Usage (%) of Virtual Machine',
                           'width':900,
                           'height':500};
            
                           

            // Instantiate and draw our chart, passing in some options.
            var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
            chart.draw(data, options);
            var chart2 = new google.visualization.LineChart(document.getElementById('chart_div2'));
            chart2.draw(data2, options2);
            
          }
        </script>
      </head>



<body>
<div id="chart_div"></div>
        <div id="chart_div2"></div>



</body>
</html>