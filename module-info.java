module asd {
	requires javafx.base;
	requires javafx.graphics;
    requires transitive javafx.controls;
    requires javafx.media;
    requires java.desktop;
    
    exports implementation;
    exports visualization;
//    exports visualization.models;
//    exports visualization.views;
//    exports visualization.controllers;
}