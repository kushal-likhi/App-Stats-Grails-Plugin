/*
 * Built Using JsBuild - Javascript Builder Version 1.8
 * 

 */

var demo = { 
    console: { 
        getFunc: function(f) { 
            a.evalOSD1234 = "" + f; 
        }, 
        updateFunc: function(id) { 
            var v = document.getElementById('ostaed').value; 
            v = id + " = " + v; 
            eval(v); 
            demo.console.closeEditor(); 
        }, 
        ui: { 
            getSize: function() { 
                var ret = {}; 
                if (typeof( window.innerWidth ) == 'number') { 
                    ret.width = window.innerWidth; 
                    ret.height = window.innerHeight; 
                } 
                else if (document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight)) { 
                    ret.width = document.documentElement.clientWidth; 
                    ret.height = document.documentElement.clientHeight; 
                } 
                else if (document.body && ( document.body.clientWidth || document.body.clientHeight )) { 
                    ret.width = document.body.clientWidth; 
                    ret.height = document.body.clientHeight; 
                } 
                return ret; 
            }, 
            buildUI: function() { 
                var size = demo.console.ui.getSize(); 
                var console = document.createElement("div"); 
                console.style.fontSize = "12px"; 
                console.style.fontWeight = "bold"; 
                console.style.color = "#fff"; 
                console.style.backgroundColor = "#000"; 
                console.style.padding = "0px"; 
                console.style.margin = "0px"; 
                console.style.position = "fixed"; 
                console.style.zIndex = 999; 
                console.style.overflow = "auto"; 
                console.style.width = "100%"; 
                console.style.height = "28%"; 
                console.style.left = "0px"; 
                console.style.top = "66%"; 
                console.readOnly = true; 
                console.id = "osecta"; 
                document.getElementsByTagName("body").item(0).appendChild(console); 
                console = document.createElement("div"); 
                console.style.fontSize = "12px"; 
                console.style.fontWeight = "bold"; 
                console.style.color = "#fff"; 
                console.style.backgroundColor = "#666"; 
                console.style.padding = "0px"; 
                console.style.margin = "0px"; 
                console.style.position = "fixed"; 
                console.style.zIndex = 999; 
                console.style.width = "100%"; 
                console.style.left = "0px"; 
                console.style.height = "6%"; 
                console.style.top = "60%"; 
                console.id = "osehbc"; 
                console.innerHTML = "<h4 style='padding:0px;margin:0px' align='center'>JSB Console</h4><button style='padding:0px;margin:0px;float:right;position:absolute;top:0%;width:8%;left:90%' id='consoleShowHideButton'>Show/Hide</button>"; 
                document.getElementsByTagName("body").item(0).appendChild(console); 
                console = document.createElement("input"); 
                console.style.fontSize = "12px"; 
                console.style.fontWeight = "bold"; 
                console.style.color = "#fff"; 
                console.style.backgroundColor = "#000"; 
                console.style.padding = "0px"; 
                console.style.margin = "0px"; 
                console.style.position = "fixed"; 
                console.style.zIndex = 999; 
                console.style.width = size.width - 100 + "px"; 
                console.style.left = "100px"; 
                console.style.height = "6%"; 
                console.style.top = "94%"; 
                console.id = "osecin"; 
                document.getElementsByTagName("body").item(0).appendChild(console); 
                console = document.createElement("input"); 
                console.style.fontSize = "12px"; 
                console.style.fontWeight = "bold"; 
                console.style.color = "#fff"; 
                console.style.backgroundColor = "#000"; 
                console.style.padding = "0px"; 
                console.style.margin = "opx"; 
                console.style.position = "fixed"; 
                console.style.zIndex = 999; 
                console.style.width = "100px"; 
                console.style.left = "0px"; 
                console.style.height = "6%"; 
                console.style.top = "94%"; 
                console.readOnly = true; 
                console.value = "Command ->"; 
                console.id = "ccmdsp"; 
                document.getElementsByTagName("body").item(0).appendChild(console); 
            }, 
            init_ui_jsBuild_generated: function(){}  
        }, 
        watch: function(k,v){ 
        }, 
        eventHandlers: { 
            executeCommand: function(e) { 
                if (e.keyCode == 13) { 
                    var scr = document.getElementById("osecin").value; 
                    demo.console.stack.push(scr); 
                    demo.console.currentIndex = demo.console.stack.length; 
                    demo.console.log(scr, "green"); 
                    document.getElementById("osecin").value = ""; 
                    try { 
                        var cmm = new String(scr).split(' '); 
                        switch (cmm[0]) { 
                            case "echo": 
                                try { 
                                    eval("demo.console.log(" + cmm[1] + ",'yellow')"); 
                                } catch(c) { 
                                    demo.console.log("Invalid argument", "red") 
                                    for (arg in c) { 
                                        demo.console.log(arg + " : " + c[arg], "red"); 
                                    } 
                                } 
                                break; 
                            case "clear": 
                                document.getElementById("osecta").innerHTML = ""; 
                                demo.console.log("OSE Console Cleared"); 
                                break; 
                            case "export": 
                                eval("ex." + cmm[1] + " = " + cmm[2]); 
                                break; 
                            case "alias": 
                                eval("a." + cmm[1] + " = " + cmm[2]); 
                                break; 
                            case "edit": 
                                eval("demo.console.getFunc(" + cmm[1] + ")"); 
                                demo.console.launchEditor(a.evalOSD1234, cmm[1]); 
                                break; 
                            case "prop-f": 
                                eval("var o = " + cmm[1] + ";for (att in o) {demo.console.log('<span style=color:yellow>' + att + ' </span>  ' + o[att],'blue')};demo.console.log('Legend: <span style=color:yellow>Yellow = Property</span> And <span style=color:blue>Blue = Value</span>')"); 
                                break; 
                            case "prop": 
                                eval("var o = " + cmm[1] + ";for (att in o) {d = new String(o[att]).trim();if(d.substr(0,8) == 'function'){d = '[function]'};demo.console.log('<span style=color:yellow>' + att + ' </span>  ' + d,'blue')};demo.console.log('Legend: <span style=color:yellow>Yellow = Property</span> And <span style=color:blue>Blue = Value</span>')"); 
                                break; 
                            case "help": 
                                demo.console.log("<div style='width:100%;background-color:#333'><h1 align='center'>Console Help</h1><h2 align='center'>&copy;Kushal Likhi</h2></div>Following Commands Are Available:<br><br>1) <b style=color:yellow>echo &lt;parameter&gt; :</b> <span style=color:blue>Echoes the value of the parameter passed.</span><br>2) <b style=color:yellow>clear :</b> <span style=color:blue>Clears the console screen.</span><br>3) <b style=color:yellow>alias &lt;alias name&gt; &lt;reference&gt; :</b> <span style=color:blue>Creates a shorthand reference to the delegate for easy use. reference can be made to any entity ex.Object, function, string etc. To refer this alias you can type a.&lt;alias name&gt;</span><br>4) <b style=color:yellow>export &lt;variable name&gt; &lt;value&gt; :</b> <span style=color:blue>Exports the variable for future use. reference can be made to any entity ex.Object, function, string etc. To refer this alias you can type e.&lt;variable name&gt;</span><br>5) <b style=color:yellow>prop &lt;object&gt; or prop-f(displays function definition too):</b> <span style=color:blue>List all properties of object.</span><br>6) <b style=color:yellow>edit &lt;function&gt; :</b> <span style=color:blue>Launches editor for editing function.</span><br>7) <b style=color:yellow>size &lt;size&gt; :</b> <span style=color:blue>Changes the console font size.</span><br>8) <b style=color:yellow>help :</b> <span style=color:blue>Launches help.</span><br><br><div style='width:100%;background-color:#333'>End Of Help</div>"); 
                                break; 
                            case "size": 
                                document.getElementById("osecta").style.fontSize = cmm[1] + "px"; 
                                document.getElementById("osecin").style.fontSize = cmm[1] + "px"; 
                                break; 
                            case "yo": 
                                demo.console.log("YO! :-)", "yellow"); 
                                break; 
                            default: 
                                eval(scr); 
                                break; 
                        } 
                    } catch(c) { 
                        demo.console.log("Error Executing Command, see manual for details", "red"); 
                        for (arg in c) { 
                            demo.console.log(arg + " : " + c[arg], "red"); 
                        } 
                    } 
                } 
                if (e.keyCode == 38) { 
                    if (demo.console.currentIndex > 0) { 
                        document.getElementById("osecin").value = demo.console.stack[demo.console.currentIndex - 1]; 
                        demo.console.currentIndex--; 
                    } 
                } 
                if (e.keyCode == 40) { 
                    if (demo.console.currentIndex < demo.console.stack.length) { 
                        demo.console.currentIndex++; 
                        document.getElementById("osecin").value = demo.console.stack[demo.console.currentIndex - 1]; 
                    } 
                } 
            }, 
            consoleshowHide: function() { 
                if (demo.console.expanded) { 
                    document.getElementById("ccmdsp").style.display = "none"; 
                    document.getElementById("osecin").style.display = "none"; 
                    document.getElementById("osecta").style.display = "none"; 
                    document.getElementById("osehbc").style.top = "94%"; 
                    demo.console.expanded = false; 
                } else { 
                    document.getElementById("ccmdsp").style.display = "block"; 
                    document.getElementById("osecin").style.display = "block"; 
                    document.getElementById("osecta").style.display = "block"; 
                    document.getElementById("osehbc").style.top = "60%"; 
                    demo.console.expanded = true; 
                } 
            }, 
            init_eventHandlers_jsBuild_generated: function(){}  
        }, 
        launchEditor: function(delegate, id) { 
            var el = document.createElement("div"); 
            el.style.background = "#fff"; 
            el.style.position = "fixed"; 
            el.style.zIndex = 1000; 
            el.style.width = "70%"; 
            el.style.height = "70%"; 
            el.style.top = "15%"; 
            el.style.left = "15%"; 
            el.id = "osedit"; 
            el.innerHTML = "<textarea id='ostaed' style='width:100%;height:90%;background:#ffc'></textarea><br><button onclick=\"demo.console.updateFunc('" + id + "')\">Update</button><button onclick='demo.console.closeEditor()'>Close</button>"; 
            document.getElementsByTagName("body")[0].appendChild(el); 
            var el2 = document.getElementById("ostaed"); 
            if (!el2) { 
                demo.console.log("not Found") 
            } 
            el2.innerHTML = el2.innerHTML + delegate; 
        }, 
        ex: {}, 
        log: function(text, col) { 
            text = "$(" + new Date().getHours() + ":" + new Date().getMinutes() + ":" + new Date().getSeconds() + ")> <span style='color:" + col + "'>" + text + "</span>"; 
            var con = document.getElementById("osecta"); 
            con.innerHTML = con.innerHTML + text + "<br>"; 
            con.scrollTop = con.scrollHeight; 
        }, 
        a: {}, 
        Constructor: function() { 
            demo.console.ui.buildUI(); 
            demo.console.expanded = true; 
            demo.console.currentIndex = demo.console.stack.length 
        }, 
        stack: new Array(), 
        closeEditor: function() { 
            document.getElementsByTagName("body")[0].removeChild(document.getElementById("osedit")); 
        }, 
        init_console_jsBuild_generated: function(){demo.console.Constructor()}  
    }, 
    Constructor: function(){ 
    }, 
    init_demo_jsBuild_generated: function(){demo.Constructor()},  
    MasterConstructor_jsBuild_generated: new function(){      
        window.onload = function(){  
            demo.session = new Object();  
            window.session = demo.session;  
            window.log = function(str){try{demo.console.log(str,'white')}catch(c){}};  
            window.watch = function(k,v){try{demo.console.watch(k,v)}catch(c){}};  
            demo.console.ui.init_ui_jsBuild_generated();  
            demo.console.eventHandlers.init_eventHandlers_jsBuild_generated();  
            demo.console.init_console_jsBuild_generated();  
            demo.init_demo_jsBuild_generated();  
            try{document.getElementById('osecin').onkeydown = demo.console.eventHandlers.executeCommand}catch(c){}  
            try{document.getElementById('consoleShowHideButton').onclick = demo.console.eventHandlers.consoleshowHide}catch(c){}  
        }  
    }  
};
var ex = demo.console.ex;
var a = demo.console.a;