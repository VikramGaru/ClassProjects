#ifndef HW2_FIREWORKFACTORY_H
#define HW2_FIREWORKFACTORY_H

#include <stack>
#include <queue>
#include <vector>

#include "Firework.h"

class FireworkFactory {

private:
queue<Firework*> _tinventory;
queue<Firework*> _op;
queue<Firework*> _gp;
queue<Firework*> _bp;
queue<Firework*> _pp;
int o=0;
int g=0;
int b=0;
int p=0;
    // TODO: Track quantities of metals
    // TODO: Track finished fireworks in the proper order

public:
    FireworkFactory();

    // Point 1
    void fireworkShipment(stack<Firework*>&);
    void sellFireworks(stack<Firework*>&, int quantity);

    // Point 2
    void metalShipment(stack<Metal>&);
    ~FireworkFactory();

    // Point 3 for correctness, Point 4 for O(1) runtime
    void sellFireworks(stack<Firework*>&, int quantity, Color color);
};


#endif //HW2_FIREWORKFACTORY_H
