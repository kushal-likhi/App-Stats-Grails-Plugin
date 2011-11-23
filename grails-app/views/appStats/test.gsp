<%--
  Created by IntelliJ IDEA.
  User: kushal
  Date: 9/30/11
  Time: 3:30 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>

    <script type="text/javascript">

        var jsLocalSystemFileProvider = {
            defaults: {
                name:null,
                size:null,
                binary:this,
                text:null,
                dataUrl: null
            },
            classes: {
                JSLocalSystemFile: function(options) {
                    var enc = (typeof(options.encoding) == 'undefined') ? "UTF-8" : options.encoding;
                    var props = jsLocalSystemFileProvider.defaults;
                    var element = document.getElementById(options.id);
                    var obj = this;
                    this.errors = [];
                    this.getSize = function() {
                        return props.size;
                    };
                    this.getBinary = function() {
                        return props.binary;
                    };
                    this.getName = function() {
                        return props.name;
                    };
                    this.getText = function() {
                        return props.text;
                    };
                    this.getDataUrl = function() {
                        return props.dataUrl;
                    };
                    var idx = 0;
                    var reporter = function(prop, val) {
                        props[prop] = val;
                        idx++;
                        if (idx == 5) {
                            options.onready(obj);
                        }
                    };
                    if (element) {
                        jsLocalSystemFileProvider.processRequest(element, enc, reporter, obj);
                    }
                }
            },
            inject: new function() {
                String.prototype.getFile = function() {
                    return new jsLocalSystemFileProvider.classes.JSLocalSystemFile(this.toString());
                }
            },
            processRequest: function(element, encoding, reporter, ctx) {
                var file;
                if (window.FileReader) {
                    file = element.files.item(0);
                    var treader = new FileReader();
                    treader.readAsText(file, encoding);
                    treader.onload = function (evt) {
                        reporter("text", evt.target.result);
                    };
                    treader.onerror = function (evt) {
                        ctx.errors.push("error reading file");
                    };
                    var breader = new FileReader();
                    breader.readAsBinaryString(file);
                    breader.onload = function (evt) {
                        reporter("binary", evt.target.result);
                    };
                    breader.onerror = function (evt) {
                        ctx.errors.push("error reading file in binary mode");
                    };
                    var dreader = new FileReader();
                    dreader.readAsDataURL(file);
                    dreader.onload = function (evt) {
                        reporter("dataUrl", evt.target.result);
                    };
                    dreader.onerror = function (evt) {
                        ctx.errors.push("error reading file in dataUrl mode");
                    };
                    reporter("name", file.name);
                    reporter("size", file.size);
                } else if (element.files) {
                    file = element.files.item(0);
                    try {
                        reporter("binary", file.getAsBinary());
                    } catch(c) {
                        ctx.errors.push("error reading file in binary mode: b");
                    }
                    try {
                        reporter("text", file.getAsText(encoding));
                    } catch(c) {
                        ctx.errors.push("error reading file in text mode: b");
                    }
                    try {
                        reporter("dataUrl", file.getAsDataURL());
                    } catch(c) {
                        ctx.errors.push("error reading file in dataUrl mode: b");
                    }
                    try {
                        reporter("name", file.fileName);
                    } catch(c) {
                        ctx.errors.push("error reading file Name: b");
                    }
                    try {
                        reporter("size", file.fileSize);
                    } catch(c) {
                        ctx.errors.push("error reading file size: b");
                    }
                } else {
                    // the IE Style
                    var fileName = element.value;
                    /*
                    * try
    {
        var fso  = new ActiveXObject("Scripting.FileSystemObject");
        var fh = fso.OpenTextFile(filename, 1);
        var contents = fh.ReadAll();
        fh.Close();
        return contents;
    }
    catch (Exception)
    {
        return "Cannot open file :(";
    }*/
                }
            }
        };
        var JSLocalSystemFile = jsLocalSystemFileProvider.classes.JSLocalSystemFile;

        var file;
        function doIt() {
            file = new JSLocalSystemFile({id:"yo",onready:function(obj) {
                alert(obj.getName());
                alert(obj.getSize());
                alert(obj.getText());
                alert(obj.getBinary());
                alert(obj.getDataUrl());
                alert(obj.errors)
            }});
        }


    </script>



    <title>Simple GSP page</title>

</head>

<body>Place your content here
<input id="yo" type="file" onchange="doIt()"/>
</body>
</html>