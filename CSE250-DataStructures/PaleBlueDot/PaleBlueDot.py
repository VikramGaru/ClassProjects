import datetime
import urllib.request
import math
import json
import hashlib

# These imports may be helpful. Feel free to add more if needed


# Location class used to communicate lat/long
class Location:
    # Do not put member variable outside the constructor
    # Variables declared here become static class variables

    # constructor in python. "self" is similar to "this" and must be the first parameter in every method declaration
    def __init__(self, latitude, longitude):
        # self variables declared in the constructor become member variables
        self.latitude = latitude
        self.longitude = longitude if longitude < 180.0 else longitude - 360.0

    def getLatitude(self):
        return self.latitude

    def getLongitude(self):
        return self.longitude

    def __str__(self):
        return "(" + str(self.latitude) + "," + str(self.longitude) + ")"

    def __eq__(self, other):
        return ((int((self.latitude)) == int((other.latitude))) and (int((self.longitude)) == int((other.longitude))))

    def __hash__(self):
        return hash((int((self.latitude)),int((self.longitude))))


# The primary class that needs to be implemented. Some skeleton code has been provided, but is not necessary. Feel free
# to structure your code any way you want as long as the required methods function properly.
class PaleBlueDot:
    # Do not put member variable outside the constructor
    # Variables declared here become static class variables

    # Parse files and initialize all data structures. The citiesFile is in the format of worldCities.csv
    def __init__(self, citiesFile):
        self.cityLocations = {}  # initialize a dictionary. You can use any data structure you choose
        self.observatories = []  # initialize a list. You can use any data structure you choose
        self.parseCities(citiesFile)
        self.getObservatories()

    def parseCities(self, citiesFile):
        cities = open(citiesFile, "r")  # open a file in read mode
        cities.readline()  # remove source
        cities.readline()  # remove header
        for line in cities.readlines():
            # Country,City,Region,Latitude,Longitude
            # TODO
            a=line.split(',')
            k=Location(float(a[3]),float(a[4]))
            if k in self.cityLocations:
                b=self.cityLocations[k]
                #print(b)
                b.append(a)
                self.cityLocations[k]=b
                #print("Entered")
                #print(k)
                #print(b)
            else:
                self.cityLocations[k]=[a]
            #pass
        cities.close()

    # pull observatory information from the Internet, parse and store.
    def getObservatories(self):
        url = "http://www.minorplanetcenter.net/iau/lists/ObsCodes.html"
        # Open the url in a browser to see the format of the data.
        result = urllib.request.urlopen(url)  # download the data
        observatory = result.read().decode('utf-8')  # read the data as unicode
        for line in observatory.split("\n"):
            # TODO
            #pass
            try:
                cd=line[:3].strip()
                lt=float(line[4:13].strip())
                nm=line[30:].strip()
                s=float(line[21:30].strip())
                c=float(line[13:21].strip())
                if c!=0:
                    t=math.degrees(math.atan(s/c))
                else:
                    t=90
                a=[cd,lt,t,nm]
                self.observatories.append(a)
            except:
                continue

    # part 1 - String Parsing

    '''
    Given the name a city, its region, and country, returns its location as a Location object. Returns an empty list
    if the city is unknown, though bad inputs will not be tested for grading so an error can be thrown instead.
    '''

    def getCityLocation(self, city, region, country):
        #pass
        a=self.cityLocations.keys()
        for keys in a:
            b=self.cityLocations[keys]
            for c in b:
                if c[0]==country and c[1]==city and c[2]==region:
                    return Location(float(c[3]),float(c[4]))
        return []
    '''
    Given a 3 digit observatory code as a string, return the name of the corresponding observatory.

    Current data can be found here: http://www.minorplanetcenter.net/iau/lists/ObsCodes.html

    Note that this data is not in the most friendly format and care must be taken while parsing. Non-existing codes
    will not be tested.
    '''

    def getObservatoryName(self, observatoryCode):
        #pass
        for o in self.observatories:
            if str(o[0])==observatoryCode:
                return o[3]

    # part 2 - Math

    '''
    Given a 3 digit observatory code as a string, return the location of the corresponding observatory
    as a Location object with lat /long in degrees. Note that the data is given as
    longitude (in degrees), cos, and sin. Computing atan(sin/cos) will give the latitude in radians.
    Non-existing codes will not be tested.
    '''

    def getObservatoryLocation(self, observatoryCode):
        #pass
        for o in self.observatories:
            if str(o[0])==observatoryCode:
                return Location(o[2],o[1])

    '''
    Return the great circle distance between two locations on Earth in kilometers. For information on great circle
    distance including sample code in JavaScript see: http://www.movable-type.co.uk/scripts/latlong.html
    '''

    def greatCircleDistance(self, location1, location2):
        #pass
        R=6371000        #metres
        l1=math.radians((location1.getLatitude()))
        l2=math.radians((location2.getLatitude()))
        dlt=l2-l1
        dld=math.radians((location2.getLongitude()-location1.getLongitude()))
        a=math.sin(dlt/2)*math.sin(dlt/2)+math.cos(l1)*math.cos(l2)*math.sin(dld/2)*math.sin(dld/2)
        c=2*math.atan2(math.sqrt(a),math.sqrt(1-a))		
        d=R*c
        return d/1000.0
    # part 3

    '''
    Given a location on Earth by lat/long, return the name of the code of the closest observatory in terms of
    great circle distance
    '''

    def getClosestObservatory(self, location):
        #pass
        ob=Location(self.observatories[0][2],self.observatories[0][1])
        d1=self.greatCircleDistance(location,ob)
        nm=self.observatories[0][0]
        for observatory in self.observatories:
            d2=self.greatCircleDistance(location,Location(observatory[2],observatory[1]))
            if d2<d1:
                nm=observatory[0]
                d1=d2
        return nm

    '''
    Return the code of the observatory that is closest to the ISS. ISS location can be obtained through
    this API: http://api.open-notify.org/
    The result will be in json form which python will parse using "json.loads(jsonData)"
    '''

    def getClosestObservatoryToISS(self):
        #pass
        req=urllib2.Request("http://api.open-notify.org/iss-now.json")
        response=urllib2.urlopen(req)
        obj=json.loads(response.read())
        a=Location(obj['data']['iss_position']['latitude'],obj['data']['iss_position']['longitude'])
        return getClosestObservatory(a)
    '''
    Return the next chance to observe the ISS from the given location. Use the same API from the previous
    method, but call "pass time" to retrieve the relevant data. Parsing the JSON will result in a unix
    timestamp that must be converted and returned in a user friendly format via:
    datetime.datetime.fromtimestamp(int("1457985637")).strftime('%Y-%m-%d %H:%M:%S')
    This is the format expected during testing.
    '''

    def nextPassTime(self, location):
        #pass
        LAT=location.getLatitude()
        LON=location.getLongitude()
        req=urllib2.Request("http://api.open-notify.org/iss-pass.json?lat=LAT&lon=LON")
        response=urllib2.urlopen(req)
        obj=json.loads(response.read())
        a=obj['data']['request']['datetime']
        return datetime.datetime.fromtimestamp(int(a)).strftime('%Y-%m-%d %H:%M:%S')
    # part 4

    '''
    Given a location on Earth by lat/long, return the name of the closest city in terms of
    great circle distance. Credit will only be given if the efficiency bound is met. No
    partial credit for correctness. Return city as a list in the form [city,region,countryCode]
    '''

    def getClosestCity(self, location):
        #pass
        list=[]
        k=location
        i=1
        #print(Location(17,-26) in self.cityLocations)
        #print(len(self.cityLocations.keys()))
        count=0
        md=-1
        #for k1 in self.cityLocations.keys():
            #print(k1)
        while(count==0):
            if k in self.cityLocations:
                #print("Enter")
                b=self.cityLocations[k]
                d=self.greatCircleDistance(location,Location(float(b[0][3]),float(b[0][4])))
                for a in b:
                    c=self.greatCircleDistance(location,Location(float(a[3]),float(a[4])))
                    if c<d:
                        d=c
                        list=[a[1],a[2],a[0]]
                break
            if int(location.getLatitude())==90 or int(location.getLatitude())==-90:
                 if int(location.getLatitude)==90:
                     x=int(math.floor(k.getLatitude()))-i
                     j=180
                     while(j>=-180):
                         k1=Location(x,j)
                         if k1 in self.cityLocations.keys():
                             count=count+1
                             b=self.cityLocations[k1]
                             for a in b:
                                 c=self.greatCircleDistance(location,Location(float(a[3]),float(a[4])))
                                 if c<md or md==-1:
                                     list=[a[1],a[2],a[0]]
                                     md=c
                         j=j-1
                 else:
                     x=int(math.floor(k.getLatitude()))+i
                     j=180
                     while(j>=-180):
                         k1=Location(x,j)
                         if k1 in self.cityLocations.keys():
                             count=count+1
                             b=self.cityLocations[k1]
                             for a in b:
                                 c=self.greatCircleDistance(location,Location(float(a[3]),float(a[4])))
                                 if c<md or md==-1:
                                     list=[a[1],a[2],a[0]]
                                     md=c
                         j=j-1
            else:
                w=int((k.getLatitude()))+i
                x=int((k.getLatitude()))-i
                y=int((k.getLongitude()))+i
                z=int((k.getLongitude()))-i
                #w=(k.getLatitude())+i
                #x=(k.getLatitude())-i
                #y=(k.getLongitude())+i
                #z=(k.getLongitude())-i
                #if w>90:
                 #   w=90+(90-w)
                #if x<-90:
                 #   x=-90+(90+x)
                #if y>180:
                 #   y=(y-180)-180
                #if z<-180:
                 #   z=-180+(180+z)
                w1=w
                x1=x
                y1=y
                z1=z
                while(w1>=x1):
                    k1=Location(float(w1),float(y))
                    k2=Location(float(w1),float(z))
                    #print(w1,y)
                    #print(w1,z)
                    if k1 in self.cityLocations:
                        count=count+1
                        b=self.cityLocations[k1]
                        for a in b:
                            c=self.greatCircleDistance(location,Location(float(a[3]),float(a[4])))
                            if c<md or md==-1:
                                list=[a[1],a[2],a[0]]
                                md=c
                    if k2 in self.cityLocations:
                        count=count+1
                        b=self.cityLocations[k2]
                        for a in b:
                            c=self.greatCircleDistance(location,Location(float(a[3]),float(a[4])))
                            if c<md or md==-1:
                                list=[a[1],a[2],a[0]]
                                md=c
                    w1=w1-1
                while(y1>=z1):
                    k3=Location(float(w),float(y1))
                    k4=Location(float(x),float(y1))
                    #print(w,y1)
                    #print(x,y1)
                    if k3 in self.cityLocations:
                        count=count+1
                        b=self.cityLocations[k3]
                        for a in b:
                            c=self.greatCircleDistance(location,Location(float(a[3]),float(a[4])))
                            if c<md or md==-1:
                                list=[a[1],a[2],a[0]]
                                md=c
                    if k4 in self.cityLocations:
                        count=count+1
                        b=self.cityLocations[k4]
                        for a in b:
                            c=self.greatCircleDistance(location,Location(float(a[3]),float(a[4])))
                            if c<md or md==-1:
                                list=[a[1],a[2],a[0]]
                                md=c
                    y1=y1-1
            i=i+1
            #print(i)
            #print(count)
        return list

    '''
    Return the closest city to the ISS. Return city as a list in the form [city,region,countryCode]
    '''

    def getClosestCityToISS(self):
        #pass
        req=urllib2.Request("http://api.open-notify.org/iss-now.json")
        response=urllib2.urlopen(req)
        obj=json.loads(response.read())
        a=Location(obj['data']['iss_position']['latitude'],obj['data']['iss_position']['longitude'])
        return getClosestCity(a)
