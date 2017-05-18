#include "StockTracker.h"

#include <string>
#include <vector>
#include <map>

using namespace std;

StockTracker::StockTracker(){}


void StockTracker::registerTicker(string tickerSymbol, int sharesOutstanding){
    // TODO
	bool a=isTicker(tickerSymbol);
	_so.insert(pair<string,int>(tickerSymbol,sharesOutstanding));
	_sp.insert(pair<string,double>(tickerSymbol,0.0));
	if(!a){
		_a.insert(pair<double,string>(0.0,tickerSymbol));
	}
}

bool StockTracker::isTicker(string tickerSymbol){
    // TODO
	map<string,int>::iterator it=_so.find(tickerSymbol);
	if(it!=_so.end()){
		return true;
	}
	return false;
}

int StockTracker::getSharesOutstanding(string tickerSymbol){
    // TODO
	map<string,int>::iterator it=_so.find(tickerSymbol);
	if(it!=_so.end()){
		return it->second;
	}
	return 0;
}


void StockTracker::updateCurrentPrice(string tickerSymbol, double price){
    // TODO
	int i=0;
	bool a=isTicker(tickerSymbol);
	double op=getMarketCap(tickerSymbol);
	double np=price*getSharesOutstanding(tickerSymbol);
	multimap<double,string>::iterator it=_a.find(op);
	if(a){
		_sp[tickerSymbol]=price;
	}
	while(a){
		if(tickerSymbol==(it->second)){
			_a.erase(it);
			i=1;
			a=false;
		}
		it++;
	}
	if(i==1){
		_a.insert(pair<double,string>(np,tickerSymbol));
	}
}

double StockTracker::getCurrentPrice(string tickerSymbol){
    // TODO
	map<string,double>::iterator it=_sp.find(tickerSymbol);
	if(it!=_sp.end()){
		return it->second;
	}
	return 0.0;
}

double StockTracker::getMarketCap(string tickerSymbol){
    // TODO
	double a=getCurrentPrice(tickerSymbol);
	int b=getSharesOutstanding(tickerSymbol);
	return (a*b);
}


vector<string> StockTracker::topMarketCaps(int k){
    // TODO
	vector<string> b;
	int i=0;
	multimap<double,string>::reverse_iterator it=_a.rbegin();
	while(i<k && it!=_a.rend()){
		b.push_back(it->second);
		i++;
		it++;
	}
	return b;
}