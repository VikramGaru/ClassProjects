,encoding='UTF-8'
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                k1=Location(w,y)
                md=-1
                if k1 in self.cityLocations:
                    b=self.cityLocations[k1]
                    tlist=[]
                    print("Enter")
                    d=greatCircleDistance(location,Location(float(b[0][3]),float(b[0][4])))
                    for a in b:
                        c=greatCircleDistance(location,Location(float(a[3]),float(a[4])))
                        if c<d:
                            d=c
                            md=c
                            tlist=[a[1],a[2],a[0]]
                    if md>d or md==-1:
                        md=d
                        list=tlist
                k2=Location(w,k.getLongitude())
                if k2 in self.cityLocations:
                    b=self.cityLocations[k2]
                    tlist=[]
                    print("Enter")
                    d=greatCircleDistance(location,Location(float(b[0][3]),float(b[0][4])))
                    for a in b:
                        c=greatCircleDistance(location,Location(float(a[3]),float(a[4])))
                        if c<d:
                            d=c
                            tlist=[a[1],a[2],a[0]]
                    if md>d or md==-1:
                        md=d
                        list=tlist
                k3=Location(k.getLatitude(),y)
                if k3 in self.cityLocations:
                    b=self.cityLocations[k3]
                    tlist=[]
                    print("Enter")
                    d=greatCircleDistance(location,Location(float(b[0][3]),float(b[0][4])))
                    for a in b:
                        c=greatCircleDistance(location,Location(float(a[3]),float(a[4])))
                        if c<d:
                            d=c
                            md=c
                            tlist=[a[1],a[2],a[0]]
                    if md>d or md==-1:
                        md=d
                        list=tlist
                k4=Location(w,z)
                if k4 in self.cityLocations:
                    b=self.cityLocations[k4]
                    tlist=[]
                    print("Enter")
                    d=greatCircleDistance(location,Location(float(b[0][3]),float(b[0][4])))
                    for a in b:
                        c=greatCircleDistance(location,Location(float(a[3]),float(a[4])))
                        if c<d:
                            d=c
                            md=c
                            tlist=[a[1],a[2],a[0]]
                    if md>d or md==-1:
                        md=d
                        list=tlist
                k5=Location(x,y)
                if k5 in self.cityLocations:
                    b=self.cityLocations[k5]
                    tlist=[]
                    print("Enter")
                    d=greatCircleDistance(location,Location(float(b[0][3]),float(b[0][4])))
                    for a in b:
                        c=greatCircleDistance(location,Location(float(a[3]),float(a[4])))
                        if c<d:
                            d=c
                            md=c
                            tlist=[a[1],a[2],a[0]]
                    if md>d or md==-1:
                        md=d
                        list=tlist
                k6=Location(x,z)
                if k6 in self.cityLocations:
                    b=self.cityLocations[k6]
                    tlist=[]
                    print("Enter")
                    d=greatCircleDistance(location,Location(float(b[0][3]),float(b[0][4])))
                    for a in b:
                        c=greatCircleDistance(location,Location(float(a[3]),float(a[4])))
                        if c<d:
                            d=c
                            md=c
                            tlist=[a[1],a[2],a[0]]
                    if md>d or md==-1:
                        md=d
                        list=tlist
                k7=Location(x,k.getLongitude())
                if k7 in self.cityLocations:
                    b=self.cityLocations[k7]
                    tlist=[]
                    print("Enter")
                    d=greatCircleDistance(location,Location(float(b[0][3]),float(b[0][4])))
                    for a in b:
                        c=greatCircleDistance(location,Location(float(a[3]),float(a[4])))
                        if c<d:
                            d=c
                            md=c
                            tlist=[a[1],a[2],a[0]]
                    if md>d or md==-1:
                        md=d
                        list=tlist
                k8=Location(k.getLatitude(),z)
                if k8 in self.cityLocations:
                    b=self.cityLocations[k8]
                    tlist=[]
                    print("Enter")
                    d=greatCircleDistance(location,Location(float(b[0][3]),float(b[0][4])))
                    for a in b:
                        c=greatCircleDistance(location,Location(float(a[3]),float(a[4])))
                        if c<d:
                            d=c
                            md=c
                            tlist=[a[1],a[2],a[0]]
                    if md>d or md==-1:
                        md=d
                        list=tlist
                if md!=-1:
                    break
            i=i+1
                #print(w)
                #print(x)
                #print(y)
                #print(z)
                #print()
                
                
                
                
                
                
                
                
                w1=max(x,w)
                while(w1>x):
                    pass
                    y1=max(y,z)
                    while(y1>z):
                        k1=Location(w1,y1)
                        print(w1,y1)
                        if k1 in self.cityLocations:
                            b=self.cityLocations[k1]
                            for a in b:
                                c=greatCircleDistance(location,Location(float(a[3]),float(a[4])))
                                if c<md or md==-1:
                                    list=[a[1],a[2],a[0]]
                                    md=c
                        y1=y1-1
                    w1=w1-1
                if len(list)>0:
                    break