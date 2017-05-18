#include <string>
#include "GameLogic.h"
#include "boost/algorithm/string.hpp"
using namespace boost;

/*
 *Returns whether or not the Konami Code was contained in the input
 */
bool GameLogic::containsKonamiCode(std::string inputFile){
    // TODO
	if(contains(inputFile,"uuddlrlrba")){
	return true;
	}
    return false;
}
