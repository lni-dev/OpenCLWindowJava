

#include <iostream>
#include <fstream>

#include "OpenClWindow.h"

using namespace std;

string readResourceFile(const string& name) {

    string file = "src/test/resources/" + name;
    cout << file << endl;

    std::ifstream in (file);
    std::string result (
            (std::istreambuf_iterator<char> (in)),
            std::istreambuf_iterator<char> ());
    return result;
}

int main() {

    cout << "Hello" << endl;

    auto w = linusdev::OpenClWindow();
    w.setTitle("test");
    w.setSize(800, 450);
    //w.setBorderlessFullscreen();
    w.setProgramCode(readResourceFile("render.cl"));
    w.show();

    while (!w.checkIfWindowShouldClose())
    {

        w.render();
        w.swapBuffer();
    }

    w.destroy();

    return 0;
}


