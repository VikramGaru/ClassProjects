#include "FireworkFactory.h"
using namespace std;
FireworkFactory::FireworkFactory(){
}

// Receive a stack of Firework pointers and add them to the factories inventory. Fireworks must be sold in the order
// that they are popped off the stack (The top of the stack is the oldest firework).
void FireworkFactory::fireworkShipment(stack<Firework*>& fireworkShipment){
    // TODO
	while(!fireworkShipment.empty()){
		Firework *fw=fireworkShipment.top();
		if(colorToString(fw->getColor()).compare("Orange")==0){
			_op.push(fw);
		}
		else if(colorToString(fw->getColor()).compare("Green")==0){
			_gp.push(fw);
		}
		else if(colorToString(fw->getColor()).compare("Blue")==0){
			_bp.push(fw);
		}
		else if(colorToString(fw->getColor()).compare("Purple")==0){
			_pp.push(fw);
		}
		_tinventory.push(fw);
		fireworkShipment.pop();
	}
}

// Sell quantity fireworks by pushing them onto the customerStack with the oldest fireworks being sold first (FIFO).
void FireworkFactory::sellFireworks(stack<Firework*>& customerStack, int quantity){
    // TODO
	int i=0;
	while(i<quantity && !_tinventory.empty()){
		Firework* fw=_tinventory.front();
		if(colorToString(fw->getColor()).compare("Orange")==0){
			if(fw==_op.front()){
				customerStack.push(fw);
				_op.pop();
				i=i+1;
			}
		}
		else if(colorToString(fw->getColor()).compare("Green")==0){
			if(fw==_gp.front()){
				customerStack.push(fw);
				_gp.pop();
				i=i+1;
			}
		}
		else if(colorToString(fw->getColor()).compare("Blue")==0){
			if(fw==_bp.front()){
				customerStack.push(fw);

				_bp.pop();
				i=i+1;
			}
		}
		else if(colorToString(fw->getColor()).compare("Purple")==0){
			if(fw==_pp.front()){
				customerStack.push(fw);
				_pp.pop();
				i=i+1;
			}
		}
		_tinventory.pop();
	}
}

// Before destroying the factory you must properly dispose of all the fireworks in your inventory (On the heap).
FireworkFactory::~FireworkFactory(){
    // TODO
	while(!_op.empty()){
		Firework* a=_op.front();
		delete a;
		_op.pop();
	}
	while(!_gp.empty()){
			Firework* a=_gp.front();
			delete a;
			_gp.pop();
		}
	while(!_bp.empty()){
			Firework* a=_bp.front();
			delete a;
			_bp.pop();
		}
	while(!_pp.empty()){
			Firework* a=_pp.front();
			delete a;
			_pp.pop();
		}
}

// Receive a shipment of metal which will be used to make new fireworks in the factory. Whenever the factory has 5 of
// any type of metal it must immediately make a firework of the corresponding color and add it to the inventory.
// To make a firework, you must use the new keyword to create it dynamically on the heap and manage a pointer to
// the firework.
void FireworkFactory::metalShipment(stack<Metal>& metalShipment){
    // TODO
	while(!metalShipment.empty()){
			if(colorToString(metalToColor(metalShipment.top())).compare("Orange")==0){
				o=o+1;
			}
			else if(colorToString(metalToColor(metalShipment.top())).compare("Green")==0){
				g=g+1;
			}
			else if(colorToString(metalToColor(metalShipment.top())).compare("Blue")==0){
				b=b+1;
			}
			else if(colorToString(metalToColor(metalShipment.top())).compare("Purple")==0){
				p=p+1;
			}
			if(o==5){
				Firework* fw=new Firework(metalToColor(metalShipment.top()));
				_op.push(fw);
				_tinventory.push(fw);
				o=0;
			}
			else if(g==5){
				Firework* fw=new Firework(metalToColor(metalShipment.top()));
				_gp.push(fw);
				_tinventory.push(fw);
				g=0;
			}
			else if(b==5){
				Firework* fw=new Firework(metalToColor(metalShipment.top()));
				_bp.push(fw);
				_tinventory.push(fw);
				b=0;
			}
			else if(p==5){
				Firework* fw=new Firework(metalToColor(metalShipment.top()));
				_pp.push(fw);
				_tinventory.push(fw);
				p=0;
			}
		metalShipment.pop();
	}
}

// A customer is purchasing quantity fireworks, but they must all be of the color specified by the customer. The order
// in which the fireworks are sold must be maintained (FIFO), but fireworks of the specified color can be sold before
// other colors if there is no other way to fulfil the order. Be sure not to sell the same firework more than once with
// either of the sellFireworks functions.
//
// For the efficient point of this assignment, this function must run in O(quantity) time. If only 1 firework is being
// purchased, the runtime must be O(1) regardless of where the firework to be sold is in the inventory.
void FireworkFactory::sellFireworks(stack<Firework*>& customerStack, int quantity, Color color){
    // TODO
	std::string a=colorToString(color);
	if(a.compare("Orange")==0){
		for(int i=0;i<quantity && !_tinventory.empty();i++){
			customerStack.push(_op.front());
			_op.pop();
		}
	}
	else if(a.compare("Green")==0){
		for(int i=0;i<quantity&&!_tinventory.empty();i++){
			customerStack.push(_gp.front());
			_gp.pop();
		}
	}
	else if(a.compare("Blue")==0){
		for(int i=0;i<quantity&&!_tinventory.empty();i++){
			customerStack.push(_bp.front());
			_bp.pop();
		}
	}
	else if(a.compare("Purple")==0){
		for(int i=0;i<quantity&&!_tinventory.empty();i++){
			customerStack.push(_pp.front());
			_pp.pop();
		}
	}
}
