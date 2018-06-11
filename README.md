A program that monitors weather forecasts for specific locations

To run the program please configure the application.properties

# The API shall be generated from https://openweathermap.org/api and put here
weather.monitor.app.api.key=

# Set the max temp which needs to be monitored for next 5 days
weather.monitor.app.tempmax.limit=35
#weather.monitor.app.tempmin.limit=-10

# Cities for which future weather needs to be monitored
weather.monitor.app.locations=Helsinki,Kolkata

# Periodic check interval in seconds
weather.monitor.app.schedule.period=20


#Provide the output log file full path e.g. /data/log/WeatherForecast/log/application.log or D:/log/application.log
logging.file=