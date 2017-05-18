#include "TreasureHunter.h"

TreasureHunter::TreasureHunter(int x, int y, GameBoard& board){
    // TODO
	*_b=board;
	_x=x;
	_y=y;
}
TreasureHunter::~TreasureHunter(){
    // TODO
	delete _b;
	_b=NULL;
	_x=0;
	_y=0;
}

void TreasureHunter::changeBoard(GameBoard& newBoard){
    // TODO
	
	_b=&newBoard;
}
void TreasureHunter::changeBoard(GameBoard* newBoard){
    // TODO
	_b=newBoard;
}
int TreasureHunter::computeScore(std::string inputString){
    // TODO
	int score=50;
	Location gl=_b->getGoalLocation();
	int gx=gl.getX();
	int gy=gl.getY();
	GameLogic *ko;
	bool konami=ko->containsKonamiCode(inputString);
	for(int i=0;i<inputString.length();i++){
		if(_b->isTreasure(Location(_x,_y))){
			_b->removeTreasure(Location(_x,_y));
			score=score+10;
		}
		if(gl==Location(_x,_y)){
			score=score+100;
			return score;
		}
		if(score<=0){
			return 0;
		}
		if(_b->isEnemy(Location(_x,_y))){
			for(int j=i;j<inputString.length();j++){
				i=j+1;
				if(inputString[j]=='a'){
					_b->killEnemy(Location(_x,_y));
					break;
				}
				else if(konami){
					_b->killEnemy(Location(_x,_y));
					i=j;
				}
				else if(inputString[j]!='u'||inputString[j]!='d'||inputString[j]!='l'||inputString[j]!='r'||inputString[j]!='b'){
					score=score-1;
				}
			}
		}
		if(i==inputString.length()){
			break;
		}
		char c=inputString.at(i);
		if(c=='r'){
			_x=_x+1;
			if(gx>0){
				score=score+1;
			}
			else{
				score=score-2;
			}
		}
		else if(c=='l'){
			_x=_x-1;
			if(gx<0){
				score=score+1;
			}
			else{
				score=score-2;
			}
		}
		else if(c=='u'){
			_y=_y+1;
			if(gy>0){
				score=score+1;
			}
			else{
				score=score-2;
			}
		}
		else if(c=='d'){
			_y=_y-1;
			if(gy<0){
				score=score+1;
			}
			else{
				score=score-2;
			}
		}
		else if(c!='a'||c!='b'){
			score=score-1;
		}
	}
    return score;
}