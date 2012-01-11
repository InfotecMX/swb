//Constant definitions
//Event markers
var MARKER_SIGNAL_CATCH = "images/n_senal_b.png";
var MARKER_SIGNAL_THROW = "images/n_senal_n.png";
var MARKER_MESSAGE_CATCH = "images/n_msj_b.png";
var MARKER_MESSAGE_THROW = "images/n_msj_n.png";
var MARKER_TIMER = "images/n_tmp.png";
var MARKER_RULE = "images/n_cond.png";
var MARKER_MULTI_CATCH = "images/n_multi_b.png";
var MARKER_MULTI_THROW = "images/n_multi_n.png";
var MARKER_PARALLEL = "images/n_paralelo_b.png";
var MARKER_SCALATION_CATCH = "images/n_escala_b.png";
var MARKER_SCALATION_THROW = "images/n_escala_n.png";
var MARKER_ERROR_CATCH = "images/n_error_b.png";
var MARKER_ERROR_THROW = "images/n_error_n.png";
var MARKER_COMPENSATION_CATCH = "images/n_compensa_b.png";
var MARKER_COMPENSATION_THROW = "images/n_compensa_n.png";
var MARKER_CANCEL_CATCH = "images/n_cancela_b.png";
var MARKER_CANCEL_THROW = "images/n_cancela_n.png";
var MARKER_LINK_CATCH = "images/n_enlace_b.png";
var MARKER_LINK_THROW = "images/n_enlace_n.png";
var MARKER_TERMINATION = "images/n_termina.png";

//Activity markers
var MARKER_USER = "images/n_usr.png";
var MARKER_SERVICE = "images/n_servicio.png";
var MARKER_SCRIPT = "images/n_script.png";
var MARKER_MANUAL = "images/n_manual.png";

//Activity Modifiers
var MODIFIER_LOOP = "images/n_ciclo.png";
var MODIFIER_PMULTIINSTANCE = "images/n_objeto.png";
var MODIFIER_SMULTIINSTANCE = "images/n_multi_seq.png";
var MODIFIER_COMPENSATION = MARKER_COMPENSATION_THROW;
var MODIFIER_ADHOC = "images/n_adhoc.png";
var MODIFIER_SUBPROCESS = "images/n_collapsed.png";

//Text align values
var ALIGN_CENTER = "center";
var ALIGN_LEFT = "left";
var ALIGN_RIGHT = "right";
var ALIGN_JUSTIFY = "start";
var BASELINE_TOP = "top";
var BASELINE_MIDDLE = "middle";

//------------------------------------------------------------------------------

//Modifiers definition
function Modifiers () {
    this.isForCompensation = false;
    this.isSequentialMultiInstance = false;
    this.isParallelMultiInstance = false;
    this.isLoop = false;
    this.isSubProcess = false;
    this.isAdhoc = false;
    this.spacing = 3;
}

Modifiers.prototype.setForCompensation = function (val) {this.isForCompensation = val;}
Modifiers.prototype.setModifiedElement = function (ele) {this.modifiedElement = ele;}
Modifiers.prototype.setSequentialMultiInstance = function (val) {
    this.isSequentialMultiInstance = val;
    if (val) {
        this.isParallelMultiInstance = false;
        this.isLoop = false;
    }
}
Modifiers.prototype.setParallelMultiInstance = function (val) {
    this.isForCompensation = val;
    if (val) {
        this.isSequentialMultiInstance = false;
        this.isLoop = false;
    }
}
Modifiers.prototype.setLoop = function (val) {
    this.isLoop = val;
    if (val) {
        this.isSequentialMultiInstance = false;
        this.isParallelMultiInstance = false;
    }
}
Modifiers.prototype.setAdhoc = function (val) {this.isAdhoc = val;}
Modifiers.prototype.setSubProcess = function (val) {this.isSubProcess = val;}

Modifiers.prototype.render = function (context) {
    var modifiers = [];
    
    if (this.isAdhoc) {
        var adhocModifier = new Image();
        adhocModifier.src = MODIFIER_ADHOC;
        modifiers.push(adhocModifier);
    }
    if (this.isLoop) {
        var loopModifier = new Image();
        loopModifier.src = MODIFIER_LOOP;
        modifiers.push(loopModifier);
    }
    if (this.isParallelMultiInstance) {
        var paralelModifier = new Image();
        paralelModifier.src = MODIFIER_PMULTIINSTANCE;
        modifiers.push(paralelModifier);
    }
    if (this.isSequentialMultiInstance) {
        var sequentialModifier = new Image();
        sequentialModifier.src = MODIFIER_SMULTIINSTANCE;
        modifiers.push(sequentialModifier);
    }
    if (this.isForCompensation) {
        var compensaModifier = new Image();
        compensaModifier.src = MODIFIER_COMPENSATION;
        modifiers.push(compensaModifier);
    }
    if (this.isSubProcess) {
        var subprocessModifier = new Image();
        subprocessModifier.src = MODIFIER_SUBPROCESS;
        modifiers.push(subprocessModifier);
    }
    
    var totWidth = 0;
    var maxHeight = 0;
    for (var i = 0; i < modifiers.length; i++) {
        totWidth = totWidth + modifiers[i].width + this.spacing;
        if (modifiers[i].height > maxHeight) {
            maxHeight = modifiers[i].height;
        }
    }
    var startX = this.modifiedElement.getCoords().x - this.modifiedElement.getWidth() / 2 + (this.modifiedElement.getWidth() - totWidth) / 2;
    var startY = this.modifiedElement.getCoords().y + (this.modifiedElement.getHeight()/2) - (maxHeight + 2);
    
    context.save();
    for (var i = 0; i < modifiers.length; i++) {
        context.drawImage(modifiers[i], startX, startY);
        startX = startX + modifiers[i].width + this.spacing;
    }
    context.restore();
}


//------------------------------------------------------------------------------

//EditableLabel definition
function EditableLabel(txt) {
    this.labeledElement = null;
    this.text = txt;
    this.multiLine = false;
    this.maxWidth = 50;
    this.textAlign = ALIGN_CENTER;
    this.fill = false;
}

//EditableLabel basic getters and setters
EditableLabel.prototype.setText = function (txt) {this.text = txt;}
EditableLabel.prototype.getText = function() {return this.text;}
EditableLabel.prototype.getLabeledElement = function () {return this.labeledElement;}
EditableLabel.prototype.setLabeledElement = function (le) {this.labeledElement = le;}

EditableLabel.prototype.render = function (context) {
    context.save();
    context.textBaseline = BASELINE_MIDDLE;
    context.textAlign = ALIGN_CENTER;
    context.fillText(this.text, this.labeledElement.getCoords().x, this.labeledElement.getCoords().y);
    context.restore();
}

//------------------------------------------------------------------------------

//Point definition
function Point (x, y) {
    this.x = x; //Posición X del punto
    this.y = y; //Posición Y del punto
}

//------------------------------------------------------------------------------

//ColorAdjust definition
function ColorAdjust(red, green, blue) {
    this.r = red;
    this.g = green;
    this.b = blue;
}

//------------------------------------------------------------------------------

//Marker definition
function Marker (image, offset, scale, ca) {
    this.markedElement = null;    
    this.image = new Image();   //Imagen    
    this.image.src = image;     
    this.offsetX = offset.x;    //Offset respecto al origen de la forma en X
    this.offsetY = offset.y;    //Offset respecto al origen de la forma en Y
    if (scale <= 0) {
        this.scale = 1;         //Valor para escalar el marcador
    } else {
        this.scale = scale;
    }
    this.colorAdjust = ca;      //Ajuste de color para la imagen del marcador
    this.adjusted = false;
}

//Marker basic geters and setters
Marker.prototype.setColorAdjust = function (colorAdjust) {this.colorAdjust = colorAdjust;}
Marker.prototype.getColorAdjust = function () {return this.colorAdjust;}
Marker.prototype.setMarkedElement = function (me) {this.markedElement = me;}
Marker.prototype.getMarkedElement = function () {return this.markedElement;}
Marker.prototype.AdjustColor = function () {
    var c = document.createElement("canvas");
    c.width = this.image.width;
    c.height = this.image.height;
    var ctx = c.getContext('2d');
    ctx.drawImage(this.image, 0, 0);
    var imgData = ctx.getImageData(0, 0, c.width, c.height);
    var data = imgData.data;
    for (var i = 0, n = data.length; i < n; i += 4) {
        console.log("pixel " + i + " - r: "+data[i]+", g:"+data[i+1]+", b:"+data[i+2]+", a:"+data[i+3]);
        if(data[i+3] != 0){
            data[i]   = data[i]/3; // red
            data[i+1] = data[i+1]/3; // green
            data[i+2] = data[i+2]*5; // blue
        }
    }
    return imgData;
}

//Render
//Dibuja el marcador sobre el canvas
Marker.prototype.render = function(context) {
    var imgX = this.markedElement.getCoords().x + this.offsetX;
    var imgY = this.markedElement.getCoords().y + this.offsetY;
    var imgW = this.image.width * this.scale;
    var imgH = this.image.height * this.scale;
    context.save();
//    if (!this.adjusted) {
//        this.imgD = this.AdjustColor();
//        this.adjusted = true;
//    }
//    context.putImageData(this.imgD, imgX, imgY);
    //context.putImageData(this.imgD, imgX, imgY, imgW, imgH);
    context.drawImage(this.image, imgX, imgY, imgW, imgH);
    context.restore();
}

//------------------------------------------------------------------------------

//Circle definition
function Circle(x, y, r) {
    this.x = x;         //Posición X del orígen del círculo
    this.y = y;         //Posición Y del orígen del círculo
    this.radius = r;    //Radio del círculo
}

//Circle basic getters and setters
Circle.prototype.getRadius = function()  {return this.radius;}
Circle.prototype.setRadius = function(r) {this.radius = r;}
Circle.prototype.getCoords = function()  {return new Point(this.x, this.y);}
Circle.prototype.setCoords = function(p) {this.x = p.x;this.y = p.y;}
Circle.prototype.getSroke  = function()  {return this.stroke;}
Circle.prototype.setStroke = function(s) {this.stroke = s;}
Circle.prototype.getFill   = function()  {return this.fill;}
Circle.prototype.setFill   = function(f) {this.fill = f;}
Circle.prototype.getWidth  = function()  {return this.radius * 2;}
Circle.prototype.getHeight = function()  {return this.radius * 2;}
Circle.prototype.setStrokeWidth = function(sw) {this.strokeWidth = sw;}

//Circle.render
//Dibuja un círculo en el canvas
Circle.prototype.render = function (context) {
    context.save();
    context.strokeStyle = this.stroke;
    context.lineWidth = this.strokeWidth;
    context.fillStyle = this.fill;//"#9DD49D";
    context.beginPath();
    context.arc(this.x,this.y,this.radius,0,Math.PI*2,true);
    context.closePath();
    context.stroke();
    context.translate(this.x-this.radius, this.y-this.radius);
    context.fill();
    context.restore();
}

//Circle.mouseInbounds
//Determia si la posición del mouse se encuentra dentro del círculo
Circle.prototype.mouseInBounds = function (x, y) {
    if (Math.pow(x-this.x, 2) + Math.pow(y-this.y,2) <= Math.pow(this.radius, 2)) {
        return true;
    } else {
        return false;
    }
}

//Rectangle definition
function Rectangle (x, y, width, height, cornerradius) {
    this.x = x;
    this.y = y;
    this.w = width;
    this.h = height;
    this.cr = cornerradius;
}

Rectangle.prototype.getCoords = function()  {return new Point(this.x, this.y);}
Rectangle.prototype.setCoords = function(p) {this.x = p.x;this.y = p.y;}
Rectangle.prototype.getSroke  = function()  {return this.stroke;}
Rectangle.prototype.setStroke = function(s) {this.stroke = s;}
Rectangle.prototype.getFill   = function()  {return this.fill;}
Rectangle.prototype.setFill   = function(f) {this.fill = f;}
Rectangle.prototype.getWidth  = function()  {return this.w;}
Rectangle.prototype.getHeight = function()  {return this.h;}
Rectangle.prototype.setStrokeWidth = function(sw) {this.strokeWidth = sw;}

Rectangle.prototype.render = function (context) {
    var startX = this.x - this.w/2;
    var startY = this.y - this.h/2;
    context.save();
    context.strokeStyle = this.stroke;
    context.lineWidth = this.strokeWidth;
    context.fillStyle = this.fill;//"#9DD49D";    
    context.beginPath();
    context.moveTo(startX + this.cr, startY);
    context.lineTo(startX+this.w-this.cr, startY);
    context.quadraticCurveTo(startX + this.w, startY, startX + this.w, startY + this.cr);
    context.lineTo(startX + this.w, startY + this.h - this.cr);
    context.quadraticCurveTo(startX + this.w, startY + this.h, startX + this.w - this.cr, startY + this.h);
    context.lineTo(startX + this.cr, startY + this.h);
    context.quadraticCurveTo(startX, startY + this.h, startX, startY + this.h - this.cr);
    context.lineTo(startX, startY + this.cr);
    context.quadraticCurveTo(startX, startY, startX + this.cr, startY);
    context.closePath();
    context.stroke();
    context.translate(startX-this.w/2, startY-this.h/2);
    context.fill();
    context.restore();
}

Rectangle.prototype.mouseInBounds = function (x, y) {
    var startX = this.x - this.w / 2;
    var startY = this.y - this.h / 2;
    if (x > startX && y > startY) {
        if (x < (startX + this.w) && (y < startY + this.h)) {
            return true;
        }
    }
    return false;
}

//------------------------------------------------------------------------------

//GraphicalElement definition
function GraphicalElement() {
    this.Id = 0;                    //Identificador único del evento
    this.isDragging = false;
    this.selected = false;          //Indica si el evento está seleccionado
    this.shape = null;
    this.parent = null;             //Padre del elemento en el modelo
    this.container = null;          //Contenedor del elemento en el modelo
    this.label = null;
    this.name = "GraphicalElement";
    this.modifiers = new Modifiers();
    this.modifiers.setModifiedElement(this);
}

//GraphicalElement basic getters and setters
GraphicalElement.prototype.getId = function() {return this.Id;}
GraphicalElement.prototype.setId = function(id) {this.Id = id;}
GraphicalElement.prototype.getLabel = function() {return this.label;}
GraphicalElement.prototype.setLabel = function(lbl) {this.label = lbl;lbl.setLabeledElement(this);}
GraphicalElement.prototype.setCoords = function(p) {this.shape.setCoords(p);}
GraphicalElement.prototype.getCoords = function() {return this.shape.getCoords();}
GraphicalElement.prototype.getWidth = function() {return this.shape.getWidth();}
GraphicalElement.prototype.getHeight = function() {return this.shape.getHeight();}
GraphicalElement.prototype.setMarker = function(imageMarker, colorAdjust, offset, scale) {
    this.marker = new Marker(imageMarker, offset, scale, colorAdjust);
    this.marker.setMarkedElement(this);
}

//GraphicalElement.render
//Dibuja el elemento en el canvas
GraphicalElement.prototype.render = function(context) {
    this.shape.render(context);
    if (this.marker != null) {
        this.marker.render(context);
    }
    
    if (this.modifiers != null) {
        this.modifiers.render(context);
    }
//    if (this.label != null) {
//        this.label.render(context);
//    }
}

//GraphicalElement.mouseInBounds
//Determina si el cursor se encuentra sobre este elemento
GraphicalElement.prototype.mouseInBounds = function(x, y) {
    return this.shape.mouseInBounds(x,y);
}

//GraphicalElement.hover
//Cambia el estado del elemento a 'hover'
GraphicalElement.prototype.hover = function () {
    this.shape.setStroke(this.STROKE_OVER);
    if (!this.selected) {
        this.over = true;
    }
}

//GraphicalElement.normal
//Cambia el estado del elemento a 'normal'
GraphicalElement.prototype.normal = function () {
    this.shape.setStroke(this.STROKE_NORMAL);
    this.over = this.selected = false;
}

//GraphicalElement.focus
//Cambia el estado del elemento a 'focus'
GraphicalElement.prototype.focus = function () {
    this.shape.setStroke(this.STROKE_FOCUSED);
    this.selected = true;
}

//GraphicalElement.mousePressed
//Manejador del evento 'mousePressed' del elemento
GraphicalElement.prototype.mousePressed = function (e) {
    var mouseButton = getMousePressedButton(e);
    
    if (mouseButton == "LEFT") {
        modeler.unSelectAll();
        this.focus();
        this.isDragging = true;        
    } else if (mouseButton == "RIGHT") {

    }
}

//GraphicalElement.mouseClicked
//Manejador del evento 'mouseClicked' del elemento
GraphicalElement.prototype.mouseClicked = function (e) {
    var mouseButton = getMousePressedButton(e);
    
    if (mouseButton == "LEFT") {
        
    } else if (mouseButton == "RIGHT") {

    }
}

//GraphicalElement.mouseReleased
//Manejador del evento 'mouseReleased' del elemento
GraphicalElement.prototype.mouseReleased = function (e) {
    var mouseButton = getMousePressedButton(e);
    
    if (mouseButton == "LEFT") {
        if (this.selected == true) {
            this.isDragging = false;
        }
    } else if (mouseButton == "RIGHT") {
        
    }
}

//GraphicalElement.mouseMoved
//Manejador del evento 'mouseMoved' del elemento
GraphicalElement.prototype.mouseMoved = function (e) {
    var mouseButton = getMousePressedButton(e);
    
    if (mouseButton == "LEFT") {
        if (!this.selected) {
            modeler.unHoverAll();
            this.hover();
        }
    } else if (mouseButton == "RIGHT") {

    }
}

//------------------------------------------------------------------------------

//Event definition
Event.prototype = new GraphicalElement();
Event.prototype.constructor = Event;
function Event() {
    GraphicalElement.call(this);
    this.shape = new Circle(0,0,15);//Figura principal del evento
}

//------------------------------------------------------------------------------

//CatchEvent definition
CatchEvent.prototype = new Event();
CatchEvent.prototype.constructor = CatchEvent;
function CatchEvent() {
    Event.call(this);
    this.interruptor = false;   //Indica si el evento puede ser interruptor
    this.interrupts = false;    //Indica si el evento interrumpe su tarea asociada
}

//------------------------------------------------------------------------------

//ThrowEvent definition
ThrowEvent.prototype = new Event();
ThrowEvent.prototype.constructor = ThrowEvent;
function ThrowEvent() {
    Event.call(this);
}

//------------------------------------------------------------------------------

//StartEvent definition
StartEvent.prototype = new CatchEvent();
StartEvent.prototype.constructor = StartEvent;
function StartEvent() {
    CatchEvent.call(this);
    this.STROKE_NORMAL = "#006600"; //Color de línea para el elemento en estado normal
    this.STROKE_OVER = "#FF6060";   //Color de línea para el elemento en estado over
    this.STROKE_FOCUSED = "#98FF00";//Color de línea para el elemento en estado focused

    //Gradiente para el relleno del evento
    this.gr = modeler.getContext().createLinearGradient(0,0,this.shape.getRadius(),0);
    this.gr.addColorStop(0,"#ffffff");
    this.gr.addColorStop(1,"#9DD49D");
    this.shape.setFill(this.gr);
    this.shape.setStroke(this.STROKE_NORMAL);
    this.shape.setStrokeWidth(3.5);
}

//------------------------------------------------------------------------------

//EndEvent definition
EndEvent.prototype = new ThrowEvent();
EndEvent.prototype.constructor = EndEvent;
function EndEvent() {
    ThrowEvent.call(this);
    this.STROKE_NORMAL = "#660000"; //Color de línea para el elemento en estado normal
    this.STROKE_OVER = "#FF6060";   //Color de línea para el elemento en estado over
    this.STROKE_FOCUSED = "#98FF00";//Color de línea para el elemento en estado focused

    //Gradiente para el relleno del evento
    this.gr = modeler.getContext().createLinearGradient(0,0,this.shape.getRadius(),0);
    this.gr.addColorStop(0,"#ffffff");
    this.gr.addColorStop(1,"#FAEFEF");
    this.shape.setFill(this.gr);
    this.shape.setStroke(this.STROKE_NORMAL);
    this.shape.setStrokeWidth(7);
}

//------------------------------------------------------------------------------

//NormalEndEvent definition
function NormalEndEvent () {
    EndEvent.call(this);
}
NormalEndEvent.prototype = EndEvent.prototype;
NormalEndEvent.prototype.constructor = NormalEndEvent;

//------------------------------------------------------------------------------

//ErrorEndEvent definition
function ErrorEndEvent () {
    EndEvent.call(this);
    this.setMarker(MARKER_ERROR_THROW, new ColorAdjust(10,10,10), new Point(-11,-10), 1);
}
ErrorEndEvent.prototype = EndEvent.prototype;
ErrorEndEvent.prototype.constructor = ErrorEndEvent;

//------------------------------------------------------------------------------

//MessageEndEvent definition
function MessageEndEvent () {
    EndEvent.call(this);
    this.setMarker(MARKER_MESSAGE_THROW, new ColorAdjust(10,10,10), new Point(-11,-9), 1.15);
}
MessageEndEvent.prototype = EndEvent.prototype;
MessageEndEvent.prototype.constructor = MessageEndEvent;

//------------------------------------------------------------------------------

//CancelationEndEvent definition
function CancelationEndEvent () {
    EndEvent.call(this);
    this.setMarker(MARKER_CANCEL_THROW, new ColorAdjust(10,10,10), new Point(-9,-9), 1);
}
CancelationEndEvent.prototype = EndEvent.prototype;
CancelationEndEvent.prototype.constructor = CancelationEndEvent;

//------------------------------------------------------------------------------

//CompensationEndEvent definition
function CompensationEndEvent () {
    EndEvent.call(this);
    this.setMarker(MARKER_COMPENSATION_THROW, new ColorAdjust(10,10,10), new Point(-12,-7), 1);
}
CompensationEndEvent.prototype = EndEvent.prototype;
CompensationEndEvent.prototype.constructor = CompensationEndEvent;
//------------------------------------------------------------------------------

//SignalEndEvent definition
function SignalEndEvent () {
    EndEvent.call(this);
    this.setMarker(MARKER_SIGNAL_THROW, new ColorAdjust(10,10,10), new Point(-11,-11), 1);
}
SignalEndEvent.prototype = EndEvent.prototype;
SignalEndEvent.prototype.constructor = SignalEndEvent;

//------------------------------------------------------------------------------

//MultipleEndEvent definition
function MultipleEndEvent () {
    EndEvent.call(this);
    this.setMarker(MARKER_MULTI_THROW, new ColorAdjust(10,10,10), new Point(-11.5,-12), 1);
}
MultipleEndEvent.prototype = EndEvent.prototype;
MultipleEndEvent.prototype.constructor = MultipleEndEvent;

//------------------------------------------------------------------------------

//ScalationEndEvent definition
function ScalationEndEvent () {
    EndEvent.call(this);
    this.setMarker(MARKER_SCALATION_THROW, new ColorAdjust(10,10,10), new Point(-10,-12), 1);
}
ScalationEndEvent.prototype = EndEvent.prototype;
ScalationEndEvent.prototype.constructor = ScalationEndEvent;

//------------------------------------------------------------------------------

//TerminationEndEvent definition
function TerminationEndEvent () {
    EndEvent.call(this);
    this.setMarker(MARKER_TERMINATION, new ColorAdjust(10,10,10), new Point(-10.5,-10), 1.2);
}
TerminationEndEvent.prototype = EndEvent.prototype;
TerminationEndEvent.prototype.constructor = TerminationEndEvent;

//------------------------------------------------------------------------------

//NormalStartEvent definition
function NormalStartEvent () {
    StartEvent.call(this);
}
NormalStartEvent.prototype = StartEvent.prototype;
NormalStartEvent.prototype.constructor = NormalStartEvent;

//------------------------------------------------------------------------------

//SigalStartEvent definition
function SignalStartEvent () {
    StartEvent.call(this);
    this.setMarker(MARKER_SIGNAL_CATCH, new ColorAdjust(10,10,10), new Point(-12,-13), 1.15);
}
SignalStartEvent.prototype = StartEvent.prototype;
SignalStartEvent.prototype.constructor = SignalStartEvent;

//------------------------------------------------------------------------------

//SigalStartEvent definition
function MessageStartEvent () {
    StartEvent.call(this);
    this.setMarker(MARKER_MESSAGE_CATCH, new ColorAdjust(10,10,10), new Point(-11,-10), 1.15);
}
MessageStartEvent.prototype = StartEvent.prototype;
MessageStartEvent.prototype.constructor = MessageStartEvent;

//------------------------------------------------------------------------------

//TimerStartEvent definition
function TimerStartEvent () {
    StartEvent.call(this);
    this.setMarker(MARKER_TIMER, new ColorAdjust(10,10,10), new Point(-11,-11.5), 0.95);
}
TimerStartEvent.prototype = StartEvent.prototype;
TimerStartEvent.prototype.constructor = TimerStartEvent;
    
//------------------------------------------------------------------------------

//RuleStartEvent definition
function RuleStartEvent () {
    StartEvent.call(this);
    this.setMarker(MARKER_RULE, new ColorAdjust(10,10,10), new Point(-10,-10), 1);
}
RuleStartEvent.prototype = StartEvent.prototype;
RuleStartEvent.prototype.constructor = RuleStartEvent;

//------------------------------------------------------------------------------

//MultiStartEvent definition
function MultiStartEvent () {
    StartEvent.call(this);
    this.setMarker(MARKER_MULTI_CATCH, new ColorAdjust(10,10,10), new Point(-11.5,-12.5), 1);
}
MultiStartEvent.prototype = StartEvent.prototype;
MultiStartEvent.prototype.constructor = MultiStartEvent;

//------------------------------------------------------------------------------

//ParallelStartEvent definition
function ParallelStartEvent () {
    StartEvent.call(this);
    this.setMarker(MARKER_PARALLEL, new ColorAdjust(10,10,10), new Point(-10,-10), 1);
}
ParallelStartEvent.prototype = StartEvent.prototype;
ParallelStartEvent.prototype.constructor = ParallelStartEvent;

//------------------------------------------------------------------------------

//ScalationStartEvent definition
function ScalationStartEvent () {
    StartEvent.call(this);
    this.setMarker(MARKER_SCALATION_CATCH, new ColorAdjust(10,10,10), new Point(-10,-12), 1);
}
ScalationStartEvent.prototype = StartEvent.prototype;
ScalationStartEvent.prototype.constructor = ScalationEndEvent;

//------------------------------------------------------------------------------

//ErrorStartEvent definition
function ErrorStartEvent () {
    StartEvent.call(this);
    this.setMarker(MARKER_ERROR_CATCH, new ColorAdjust(10,10,10), new Point(-10,-10), 1);
}
ErrorStartEvent.prototype = StartEvent.prototype;
ErrorStartEvent.prototype.constructor = ErrorStartEvent;

//------------------------------------------------------------------------------

//CompensationStartEvent definition
function CompensationStartEvent () {
    StartEvent.call(this);
    this.setMarker(MARKER_COMPENSATION_CATCH, new ColorAdjust(10,10,10), new Point(-12,-7), 1);
}
CompensationStartEvent.prototype = StartEvent.prototype;
CompensationStartEvent.prototype.constructor = CompensationStartEvent;

//------------------------------------------------------------------------------

//IntermediateCatchEventEvent definition
IntermediateCatchEvent.prototype = new CatchEvent();
IntermediateCatchEvent.prototype.constructor = IntermediateCatchEvent;
function IntermediateCatchEvent() {
    CatchEvent.call(this);
    this.STROKE_NORMAL = "#5e7a9e"; //Color de línea para el elemento en estado normal
    this.STROKE_OVER = "#FF6060";   //Color de línea para el elemento en estado over
    this.STROKE_FOCUSED = "#98FF00";//Color de línea para el elemento en estado focused

    //Gradiente para el relleno del evento
    this.gr = modeler.getContext().createLinearGradient(0,0,this.shape.getRadius(),0);
    this.gr.addColorStop(0,"#ffffff");
    this.gr.addColorStop(1,"#dce7f6");
    this.shape.setFill(this.gr);
    this.shape.setStroke(this.STROKE_NORMAL);
    this.shape.setStrokeWidth(3);
    
    this.shape2 = new Circle(0, 0, this.shape.getRadius() - 3);
    this.shape2.setFill(this.gr);
    this.shape2.setStroke(this.STROKE_NORMAL);
    this.shape2.setStrokeWidth(2);
}

//IntermediateCatchEvent.render
//Dibuja el evento intermedio en el canvas
IntermediateCatchEvent.prototype.render = function (context) {
    CatchEvent.prototype.render.call(this, context);
    this.shape2.render(context);
    
    if (this.marker != null) {
        this.marker.render(context);
    }
}

IntermediateCatchEvent.prototype.setCoords = function(p) {
    CatchEvent.prototype.setCoords.call(this, p);
    this.shape2.setCoords(p);
}

//------------------------------------------------------------------------------

//IntermediateThrowEventEvent definition
IntermediateThrowEvent.prototype = new ThrowEvent();
IntermediateThrowEvent.prototype.constructor = IntermediateThrowEvent;
function IntermediateThrowEvent() {
    ThrowEvent.call(this);
    this.STROKE_NORMAL = "#5e7a9e"; //Color de línea para el elemento en estado normal
    this.STROKE_OVER = "#FF6060";   //Color de línea para el elemento en estado over
    this.STROKE_FOCUSED = "#98FF00";//Color de línea para el elemento en estado focused

    //Gradiente para el relleno del evento
    this.gr = modeler.getContext().createLinearGradient(0,0,this.shape.getRadius(),0);
    this.gr.addColorStop(0,"#ffffff");
    this.gr.addColorStop(1,"#dce7f6");
    this.shape.setFill(this.gr);
    this.shape.setStroke(this.STROKE_NORMAL);
    this.shape.setStrokeWidth(3);
    
    this.shape2 = new Circle(0, 0, this.shape.getRadius() - 3);
    this.shape2.setFill(this.gr);
    this.shape2.setStroke(this.STROKE_NORMAL);
    this.shape2.setStrokeWidth(2);
}

//IntermediateCatchEvent.render
//Dibuja el evento intermedio en el canvas
IntermediateThrowEvent.prototype.render = function (context) {
    ThrowEvent.prototype.render.call(this, context);
    this.shape2.render(context);
    
    if (this.marker != null) {
        this.marker.render(context);
    }
}

IntermediateThrowEvent.prototype.setCoords = function(p) {
    ThrowEvent.prototype.setCoords.call(this, p);
    this.shape2.setCoords(p);
}

//------------------------------------------------------------------------------

//MessageIntermediateCatchEventEvent definition
function MessageIntermediateCatchEvent() {
    IntermediateCatchEvent.call(this);
    this.setMarker(MARKER_MESSAGE_CATCH, new ColorAdjust(10,10,10), new Point(-10,-8), 1);
}
MessageIntermediateCatchEvent.prototype = IntermediateCatchEvent.prototype;
MessageIntermediateCatchEvent.prototype.constructor = MessageIntermediateCatchEvent;

//------------------------------------------------------------------------------

//MessageIntermediateThrowEvent definition
function MessageIntermediateThrowEvent() {
    IntermediateThrowEvent.call(this);
    this.setMarker(MARKER_MESSAGE_THROW, new ColorAdjust(10,10,10), new Point(-10,-8), 1);
}
MessageIntermediateThrowEvent.prototype = IntermediateThrowEvent.prototype;
MessageIntermediateThrowEvent.prototype.constructor = MessageIntermediateThrowEvent;

//------------------------------------------------------------------------------

//TimerIntermediateCatchEventEvent definition
function TimerIntermediateCatchEvent() {
    IntermediateCatchEvent.call(this);
    this.setMarker(MARKER_TIMER, new ColorAdjust(10,10,10), new Point(-11,-11), .95);
}
TimerIntermediateCatchEvent.prototype = IntermediateCatchEvent.prototype;

//------------------------------------------------------------------------------

//ErrorIntermediateCatchEventEvent definition
function ErrorIntermediateCatchEvent() {
    IntermediateCatchEvent.call(this);
    this.setMarker(MARKER_ERROR_CATCH, new ColorAdjust(10,10,10), new Point(-10,-11), .95);
}
ErrorIntermediateCatchEvent.prototype = IntermediateCatchEvent.prototype;

//------------------------------------------------------------------------------

//CancelationIntermediateCatchEventEvent definition
function CancelationIntermediateCatchEvent() {
    IntermediateCatchEvent.call(this);
    this.setMarker(MARKER_CANCEL_CATCH, new ColorAdjust(10,10,10), new Point(-9,-9), .95);
}
CancelationIntermediateCatchEvent.prototype = IntermediateCatchEvent.prototype;

//------------------------------------------------------------------------------

//CompensationIntermediateCatchEventEvent definition
function CompensationIntermediateCatchEvent() {
    IntermediateCatchEvent.call(this);
    this.setMarker(MARKER_COMPENSATION_CATCH, new ColorAdjust(10,10,10), new Point(-11,-6), .95);
}
CompensationIntermediateCatchEvent.prototype = IntermediateCatchEvent.prototype;

//CompensationIntermediateThrowEvent definition
function CompensationIntermediateThrowEvent() {
    IntermediateThrowEvent.call(this);
    this.setMarker(MARKER_COMPENSATION_THROW, new ColorAdjust(10,10,10), new Point(-11,-6), .95);
}
CompensationIntermediateThrowEvent.prototype = IntermediateThrowEvent.prototype;
CompensationIntermediateThrowEvent.prototype.constructor = CompensationIntermediateThrowEvent;

//------------------------------------------------------------------------------

//RuleIntermediateCatchEventEvent definition
function RuleIntermediateCatchEvent() {
    IntermediateCatchEvent.call(this);
    this.setMarker(MARKER_RULE, new ColorAdjust(10,10,10), new Point(-9,-8), .85);
}
RuleIntermediateCatchEvent.prototype = IntermediateCatchEvent.prototype;

//------------------------------------------------------------------------------

//LinkIntermediateCatchEvent definition
function LinkIntermediateCatchEvent() {
    IntermediateCatchEvent.call(this);
    this.setMarker(MARKER_LINK_CATCH, new ColorAdjust(10,10,10), new Point(-10,-10), .95);
}
LinkIntermediateCatchEvent.prototype = IntermediateCatchEvent.prototype;

//------------------------------------------------------------------------------

//LinkIntermediateThrowEvent definition
function LinkIntermediateThrowEvent() {
    IntermediateThrowEvent.call(this);
    this.setMarker(MARKER_LINK_THROW, new ColorAdjust(10,10,10), new Point(-10,-10), .95);
}
LinkIntermediateThrowEvent.prototype = IntermediateThrowEvent.prototype;

//------------------------------------------------------------------------------

//SignalIntermediateCatchEventEvent definition
function SignalIntermediateCatchEvent() {
    IntermediateCatchEvent.call(this);
    this.setMarker(MARKER_SIGNAL_CATCH, new ColorAdjust(10,10,10), new Point(-10,-11), .95);
}
SignalIntermediateCatchEvent.prototype = IntermediateCatchEvent.prototype;

//------------------------------------------------------------------------------

//SignalIntermediateThrowEvent definition
function SignalIntermediateThrowEvent() {
    IntermediateThrowEvent.call(this);
    this.setMarker(MARKER_SIGNAL_THROW, new ColorAdjust(10,10,10), new Point(-10,-11), .95);
}
SignalIntermediateThrowEvent.prototype = IntermediateCatchEvent.prototype;
SignalIntermediateThrowEvent.prototype.prototype = SignalIntermediateThrowEvent;
//------------------------------------------------------------------------------

//MultipleIntermediateCatchEventEvent definition
function MultipleIntermediateCatchEvent() {
    IntermediateCatchEvent.call(this);
    this.setMarker(MARKER_MULTI_CATCH, new ColorAdjust(10,10,10), new Point(-11,-12), .95);
}
MultipleIntermediateCatchEvent.prototype = IntermediateCatchEvent.prototype;

//------------------------------------------------------------------------------

//MultipleIntermediateThrowEvent definition
function MultipleIntermediateThrowEvent() {
    IntermediateThrowEvent.call(this);
    this.setMarker(MARKER_MULTI_THROW, new ColorAdjust(10,10,10), new Point(-11,-12), .95);
}
MultipleIntermediateThrowEvent.prototype = IntermediateCatchEvent.prototype;

//------------------------------------------------------------------------------

//ScalationIntermediateCatchEventEvent definition
function ScalationIntermediateCatchEvent() {
    IntermediateCatchEvent.call(this);
    this.setMarker(MARKER_SCALATION_CATCH, new ColorAdjust(10,10,10), new Point(-9.5,-12), 1);
}
ScalationIntermediateCatchEvent.prototype = IntermediateCatchEvent.prototype;

//------------------------------------------------------------------------------

//ScalationIntermediateThrowEvent definition
function ScalationIntermediateThrowEvent() {
    IntermediateThrowEvent.call(this);
    this.setMarker(MARKER_SCALATION_THROW, new ColorAdjust(10,10,10), new Point(-9.5,-12), 1);
}
ScalationIntermediateThrowEvent.prototype = IntermediateCatchEvent.prototype;
ScalationIntermediateThrowEvent.prototype.constructor = ScalationIntermediateThrowEvent;

//------------------------------------------------------------------------------

//ParallelIntermediateCatchEventEvent definition
function ParallelIntermediateCatchEvent() {
    IntermediateCatchEvent.call(this);
    this.setMarker(MARKER_PARALLEL, new ColorAdjust(10,10,10), new Point(-10,-9.5), 1);
}
ParallelIntermediateCatchEvent.prototype = IntermediateCatchEvent.prototype;

//------------------------------------------------------------------------------

//Activity definition
Activity.prototype = new GraphicalElement();
Activity.prototype.constructor = Activity;
function Activity() {
    GraphicalElement.call(this);
    this.STROKE_NORMAL = "#03689a"; //Color de línea para el elemento en estado normal
    this.STROKE_OVER = "#FF6060";   //Color de línea para el elemento en estado over
    this.STROKE_FOCUSED = "#98FF00";//Color de línea para el elemento en estado focused

    this.shape = new Rectangle(0, 0, 100, 60, 5) ;//Figura principal del evento
    //Gradiente para el relleno del evento
    this.gr = modeler.getContext().createLinearGradient(0,0,this.shape.getWidth(),0);
    this.gr.addColorStop(0,"#ffffff");
    this.gr.addColorStop(1,"#BFD2E0");
    
    this.shape.setFill(this.gr);
    this.shape.setStroke(this.STROKE_NORMAL);
    this.shape.setStrokeWidth(3.5);
}

//------------------------------------------------------------------------------

//Gateway definition
Gateway.prototype = new GraphicalElement();
Gateway.prototype.constructor = Gateway;
function Gateway() {
    GraphicalElement.call(this);
    this.STROKE_NORMAL = "#03689a"; //Color de línea para el elemento en estado normal
    this.STROKE_OVER = "#FF6060";   //Color de línea para el elemento en estado over
    this.STROKE_FOCUSED = "#98FF00";//Color de línea para el elemento en estado focused

    this.shape = new Rectangle(0, 0, 100, 60, 5) ;//Figura principal del evento
    //Gradiente para el relleno del evento
    this.gr = modeler.getContext().createLinearGradient(0,0,this.shape.getWidth(),0);
    this.gr.addColorStop(0,"#ffffff");
    this.gr.addColorStop(1,"#BFD2E0");
    
    this.shape.setFill(this.gr);
    this.shape.setStroke(this.STROKE_NORMAL);
    this.shape.setStrokeWidth(3.5);
}

//------------------------------------------------------------------------------

//Task definition
Task.prototype = Activity.prototype;
Task.prototype.constructor = Task;
function Task () {
    Activity.call(this);
}

//------------------------------------------------------------------------------

//AbstractTask definition
AbstractTask.prototype = Task.prototype;
AbstractTask.prototype.constructor = AbstractTask;
function AbstractTask() {
    Task.call(this);
}

//------------------------------------------------------------------------------

//UserTask definition
UserTask.prototype = Task.prototype;
UserTask.prototype.constructor = UserTask;
function UserTask() {
    Task.call(this);
    var mx = (-1*(this.shape.getWidth()/2))+5;
    var my = (-1*(this.shape.getHeight()/2)+5);
    this.setMarker(MARKER_USER, new ColorAdjust(10,10,10), new Point(mx, my), 0.9);    
}

//------------------------------------------------------------------------------

//ServiceTask definition
ServiceTask.prototype = Task.prototype;
ServiceTask.prototype.constructor = ServiceTask;
function ServiceTask() {
    Task.call(this);
    var mx = (-1*(this.shape.getWidth()/2))+5;
    var my = (-1*(this.shape.getHeight()/2)+5);
    this.setMarker(MARKER_SERVICE, new ColorAdjust(10,10,10), new Point(mx, my), 0.7);    
}

//------------------------------------------------------------------------------

//ScriptTask definition
ScriptTask.prototype = Task.prototype;
ScriptTask.prototype.constructor = ScriptTask;
function ScriptTask() {
    Task.call(this);
    var mx = (-1*(this.shape.getWidth()/2))+5;
    var my = (-1*(this.shape.getHeight()/2)+5);
    this.setMarker(MARKER_SCRIPT, new ColorAdjust(10,10,10), new Point(mx, my), 0.7);    
}

//------------------------------------------------------------------------------

//RuleTask definition
BusinessRuleTask.prototype = Task.prototype;
BusinessRuleTask.prototype.constructor = BusinessRuleTask;
function BusinessRuleTask() {
    Task.call(this);
    var mx = (-1*(this.shape.getWidth()/2))+5;
    var my = (-1*(this.shape.getHeight()/2)+5);
    this.setMarker(MARKER_RULE, new ColorAdjust(10,10,10), new Point(mx, my), 0.8);    
}

//------------------------------------------------------------------------------

//ManualTask definition
ManualTask.prototype = Task.prototype;
ManualTask.prototype.constructor = ManualTask;
function ManualTask() {
    Task.call(this);
    var mx = (-1*(this.shape.getWidth()/2))+5;
    var my = (-1*(this.shape.getHeight()/2)+5);
    this.setMarker(MARKER_MANUAL, new ColorAdjust(10,10,10), new Point(mx, my), 0.8);
}

//------------------------------------------------------------------------------

//SendTask definition
SendTask.prototype = Task.prototype;
SendTask.prototype.constructor = SendTask;
function SendTask() {
    Task.call(this);
    var mx = (-1*(this.shape.getWidth()/2))+5;
    var my = (-1*(this.shape.getHeight()/2)+5);
    this.setMarker(MARKER_MESSAGE_THROW, new ColorAdjust(10,10,10), new Point(mx, my), 0.9);    
}

//------------------------------------------------------------------------------

//ReceiveTask definition
ReceiveTask.prototype = Task.prototype;
ReceiveTask.prototype.constructor = ReceiveTask;
function ReceiveTask() {
    Task.call(this);
    var mx = (-1*(this.shape.getWidth()/2))+5;
    var my = (-1*(this.shape.getHeight()/2)+5);
    this.setMarker(MARKER_MESSAGE_CATCH, new ColorAdjust(10,10,10), new Point(mx, my), 0.9);    
}

//------------------------------------------------------------------------------

//SubProcess definition
SubProcess.prototype = Activity.prototype;
SubProcess.prototype.constructor = SubProcess;
function SubProcess() {
    Activity.call(this);
    var mx = (-1*(this.shape.getWidth()/2))+5;
    var my = (-1*(this.shape.getHeight()/2)+5);
    this.modifiers.setSubProcess(true);
}

//------------------------------------------------------------------------------

//AdhocSubProcess definition
AdhocSubProcess.prototype = SubProcess.prototype;
AdhocSubProcess.prototype.constructor = AdhocSubProcess;
function AdhocSubProcess() {
    SubProcess.call(this);
    var mx = (-1*(this.shape.getWidth()/2))+5;
    var my = (-1*(this.shape.getHeight()/2)+5);
    this.modifiers.setAdhoc(true);
}

//------------------------------------------------------------------------------

//EventSubProcess definition
EventSubProcess.prototype = SubProcess.prototype;
EventSubProcess.prototype.constructor = EventSubProcess;
function EventSubProcess() {
    SubProcess.call(this);
    var mx = (-1*(this.shape.getWidth()/2))+5;
    var my = (-1*(this.shape.getHeight()/2)+5);
}

//------------------------------------------------------------------------------

//Transaction definition
Transaction.prototype = SubProcess.prototype;
Transaction.prototype.constructor = Transaction;
function Transaction() {
    SubProcess.call(this);
    
    this.shape2 = new Rectangle(0, 0, this.shape.getWidth() - 5, this.shape.getHeight() - 5, 5);
    this.shape2.setFill(this.gr);
    this.shape2.setStroke(this.STROKE_NORMAL);
    this.shape2.setStrokeWidth(2);
}

Transaction.prototype.render = function (context) {
    this.shape.render(context);
    if (this.shape2 != null) {
        this.shape2.render(context);
    }
    
    if (this.marker != null) {
        this.marker.render(context);
    }
    
    if (this.modifiers != null) {
        this.modifiers.render(context);
    }
}

Transaction.prototype.setCoords = function(p) {
    this.shape.setCoords(p);
    if (this.shape2 != null) {
        this.shape2.setCoords(p);
    }
}

//------------------------------------------------------------------------------

//CallActivity definition
CallActivity.prototype = Activity.prototype;
CallActivity.prototype.constructor = CallActivity;
function CallActivity() {
    Activity.call(this);
    this.shape.setStrokeWidth(6);
}

//------------------------------------------------------------------------------

//ManualCallActivity definition
ManualCallActivity.prototype = Activity.prototype;
ManualCallActivity.prototype.constructor = ManualCallActivity;
function ManualCallActivity() {
    Activity.call(this);
    this.shape.setStrokeWidth(6);
    var mx = (-1*(this.shape.getWidth()/2))+5;
    var my = (-1*(this.shape.getHeight()/2)+5);
    this.setMarker(MARKER_MANUAL, new ColorAdjust(10,10,10), new Point(mx, my), 0.8);
}

//------------------------------------------------------------------------------

//BusinessRuleCallActivity definition
BusinessRuleCallActivity.prototype = Activity.prototype;
BusinessRuleCallActivity.prototype.constructor = BusinessRuleCallActivity;
function BusinessRuleCallActivity() {
    Activity.call(this);
    this.shape.setStrokeWidth(6);
    var mx = (-1*(this.shape.getWidth()/2))+5;
    var my = (-1*(this.shape.getHeight()/2)+5);
    this.setMarker(MARKER_RULE, new ColorAdjust(10,10,10), new Point(mx, my), 0.8);
}

//------------------------------------------------------------------------------

//ScriptCallActivity definition
ScriptCallActivity.prototype = Activity.prototype;
ScriptCallActivity.prototype.constructor = ScriptCallActivity;
function ScriptCallActivity() {
    Activity.call(this);
    this.shape.setStrokeWidth(6);
    var mx = (-1*(this.shape.getWidth()/2))+5;
    var my = (-1*(this.shape.getHeight()/2)+5);
    this.setMarker(MARKER_SCRIPT, new ColorAdjust(10,10,10), new Point(mx, my), 0.7);
}

//------------------------------------------------------------------------------

//UserCallActivity definition
UserCallActivity.prototype = Activity.prototype;
UserCallActivity.prototype.constructor = UserCallActivity;
function UserCallActivity() {
    Activity.call(this);
    this.shape.setStrokeWidth(6);
    var mx = (-1*(this.shape.getWidth()/2))+5;
    var my = (-1*(this.shape.getHeight()/2)+5);
    this.setMarker(MARKER_USER, new ColorAdjust(10,10,10), new Point(mx, my), 0.9);
}

//------------------------------------------------------------------------------

//CallProcess definition
CallProcess.prototype = Activity.prototype;
CallProcess.prototype.constructor = CallProcess;
function CallProcess() {
    Activity.call(this);
    this.shape.setStrokeWidth(6);
    var mx = (-1*(this.shape.getWidth()/2))+5;
    var my = (-1*(this.shape.getHeight()/2)+5);
    this.modifiers.setSubProcess(true);
}

//------------------------------------------------------------------------------

//Modeler definition
function Modeler() {
    this.childs = [];           //Elementos del modelo
    this.selectedElements = []; //Elementos seleccionados del modelo
    this.count = 0;             //Contador de elementos para IDs
    this.cx = 0;                //Posición X actual del mouse en el canvas
    this.cy = 0;                //Posición Y actual del mouse en el canvas
    this.dx = 0;                //Incremento en X del movimiento del mouse en el canvas
    this.dy = 0;                //Incremento en Y del movimiento del mouse en el canvas
    this.context = null;        //Cotexto de dibujo del canvas
    this.stylePaddingLeft = 0;
    this.stylePaddingTop = 0;
    this.styleBorderLeft = 0;
    this.styleBorderTop = 0;
}

Modeler.prototype.getCanvasElement = function() {return this.drawingCanvas;}
Modeler.prototype.getNewId = function() {return this.count++;}
Modeler.prototype.getContext = function() {return this.context;}
Modeler.prototype.setSelectedTool = function(option) {this.selectedTool = option;}

Modeler.prototype.add = function(ge) {
    this.childs.push(ge);
    ge.render(this.context);
}

//Modeler.addSelectedElement
//Agrega un elemento al conjunto de elementos seleccionados
Modeler.prototype.addSelectedElement = function(ge) {
    this.selectedElements.push(ge);
}

Modeler.prototype.setSelectedElement = function (ge) {
    
}

//Modeler.addSelectedElement
//Agrega un elemento al conjunto de elementos seleccionados
Modeler.prototype.unSelectAll = function() {
    for (var i = this.childs.length - 1; i >= 0; i--) {
        this.childs[i].normal();
    }
}

//Modeler.unHoverAll
//Regresa todos los elementos del modelo a su estatus normal
Modeler.prototype.unHoverAll = function() {
    for (var i = this.childs.length - 1; i >= 0; i--) {
        if (!this.childs[i].selected) {
            this.childs[i].normal();
        }
    }
}

//Modeler.removeSelectedElement
////Elimina un elemento del conjunto de elementos seleccionados
Modeler.prototype.removeSelectedElement = function (ge) {
    for(var i = 0; i<this.selectedElements.length;i++ )
    {
        if(this.selectedElements[i]==ge) {
            this.selectedElements.splice(i,1); 
        }
    } 
}

//Modeler.getOverElement
//Obtiene el elemento del diagrama que se encuentra bajo el puntero del ratón
Modeler.prototype.getOverElement = function(x, y) {
    for (var i = this.childs.length - 1; i >= 0; i--) {
        if (this.childs[i].mouseInBounds(x, y)) {
            return this.childs[i];
        }
    }
    return null;
}

//Modeler.init
//Inicializa el modelador de procesos de negocio
Modeler.prototype.init = function(canvasElement) {
    this.drawingCanvas = document.getElementById(canvasElement);

    if(this.drawingCanvas.getContext) {
        this.context = this.drawingCanvas.getContext('2d');
    }

    if (document.defaultView && document.defaultView.getComputedStyle) {
        this.stylePaddingLeft = parseInt(document.defaultView.getComputedStyle(this.drawingCanvas, null)['paddingLeft'], 10) || 0;
        this.stylePaddingTop = parseInt(document.defaultView.getComputedStyle(this.drawingCanvas, null)['paddingTop'], 10) || 0;
        this.styleBorderLeft = parseInt(document.defaultView.getComputedStyle(this.drawingCanvas, null)['borderLeftWidth'], 10) || 0;
        this.styleBorderTop = parseInt(document.defaultView.getComputedStyle(this.drawingCanvas, null)['borderTopWidth'], 10) || 0;
    }

    this.drawingCanvas.addEventListener('mousemove', onMouseMoved, false);
    this.drawingCanvas.addEventListener('click', onMouseClicked, false);
    this.drawingCanvas.addEventListener('mousedown', onMousePressed, false);
    this.drawingCanvas.addEventListener('mouseup', onMouseReleased, false);
    //Inhibe la aparición del menú contextual del navegador al presionar el botón derecho del ratón
    this.drawingCanvas.oncontextmenu = function () {return false;}
}

//Modeler.render
//Actualiza el canvas y dibuja todos los elementos del diagrama
Modeler.prototype.render = function () {
    this.context.clearRect(0,0,this.drawingCanvas.width, this.drawingCanvas.height);
    for (var i = 0; i < this.childs.length; i++) {
        this.childs[i].render(this.context);
    }
}

//Modeler.getMousePosition
//Obtiene las coordenadas X,Y del puntero del ratón sobre el canvas
Modeler.prototype.getMousePosition = function (e) { 
    var element = this.drawingCanvas;
    var offsetX = 0, offsetY = 0;
        
    //Calcular offset del elemento
    if (element.offsetParent) {
        do {
            offsetX += element.offsetLeft;
            offsetY += element.offsetTop;
        } while ((element = element.offsetParent));
    }
      
    //Calcular offset de las barras
    element = this.drawingCanvas;
    do {
        offsetX -= element.scrollLeft || 0;
        offsetY -= element.scrollTop || 0;
    } while ((element = element.parentNode));

    offsetX += this.stylePaddingLeft;
    offsetY += this.stylePaddingTop;
    
    offsetX += this.styleBorderLeft;
    offsetY += this.styleBorderTop;

    this.cx = e.clientX - offsetX;
    this.cy = e.clientY - offsetY;

    if (this.cx < 0) this.cx = 0;
    if (this.cy < 0) this.cy = 0;
	
    return new Point(this.cx, this.cy);
}

Modeler.prototype.mouseClicked = function(e) {
    var mouseCoords = this.getMousePosition(e);
    var ele = this.getOverElement(mouseCoords.x, mouseCoords.y);
    
    if (ele != null) {
        ele.mouseClicked(e);
    } else {
        this.unSelectAll();
    }
    this.render();
}

//Modeler.mousePressed
//Manejador del evento onMousePressed del canvas
Modeler.prototype.mousePressed = function(e) {
    var mouseButton = getMousePressedButton(e);
    var mouseCoords = this.getMousePosition(e);
    var ele = this.getOverElement(mouseCoords.x, mouseCoords.y);
    
    if (mouseButton == "LEFT") {
        if (this.selectedTool != null) {
            this.selectedTool.setCoords(new Point(this.cx, this.cy));
            this.add(this.selectedTool);
            this.selectedTool.render(this.context);
            this.selectedTool = null;
        } else {
            if (ele != null) {
                ele.mousePressed(e);
                this.dx = this.cx - ele.getCoords().x;
                this.dy = this.cy - ele.getCoords().y;
            }
        }
    }
    this.render();
}

Modeler.prototype.mouseMoved = function(e) {
    var mouseCoords = this.getMousePosition(e);
    var ele = this.getOverElement(mouseCoords.x, mouseCoords.y);
    
    if (ele != null) {
        ele.mouseMoved(e);        
    } else {
        this.unHoverAll();
    }
    
    for (var i = this.childs.length - 1; i >= 0; i--) {
        if (this.childs[i].isDragging) {
            this.childs[i].setCoords(new Point(this.cx + this.dx, this.cy + this.dy));
        }
    }
    this.render();
}

Modeler.prototype.mouseReleased = function(e) {
    var mouseCoords = this.getMousePosition(e);
    var ele = this.getOverElement(mouseCoords.x, mouseCoords.y);
    this.dx = 0;
    this.dy = 0;
    
    if (ele != null) {
        ele.mouseReleased(e);
    }
    this.render();
}

function onMouseMoved(e) {
    modeler.mouseMoved(e);
}

function onMousePressed(e) {
    modeler.mousePressed(e);
}

function onMouseClicked(e) {
    modeler.mouseClicked(e);
}

function onMouseReleased(e) {
    modeler.mouseReleased(e);
}

function getMousePressedButton(e) {
    if (e.button) {
        return (e.button == 0) ? "LEFT" :
                ((e.button == 1) ? "MIDDLE" : "RIGHT");
    } else if (e.which) {
        return (e.which == 1) ? "LEFT" :
                ((e.which == 2) ? "MIDDLE" : "RIGHT");
    }
}