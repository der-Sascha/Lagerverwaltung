const pptxgen = require("pptxgenjs");
const React = require("react");
const RD = require("react-dom/server");
const FA = require("react-icons/fa");
const { Resvg } = require("@resvg/resvg-js");

// ---------- Palette ----------
const INK="12303A", DARK="0B3C49", TEAL="087E8B", TEAL2="028090", MINT="02C39A";
const OLD="E76F51", OLDSOFT="F4A98F", CREAM="F6F8F8", CARD="FFFFFF", MUTE="5B7179", LINE="D9E2E4";
const W=13.333, H=7.5;

// ---------- Icon rasterizer (resvg, no sharp) ----------
function iconData(name, color="#FFFFFF", size=256){
  const Comp = FA[name];
  let svg = RD.renderToStaticMarkup(React.createElement(Comp,{color,size:String(size)}));
  svg = svg.replace(/currentColor/g, color);
  if(!/xmlns=/.test(svg)) svg = svg.replace("<svg","<svg xmlns=\"http://www.w3.org/2000/svg\"");
  const png = new Resvg(svg,{fitTo:{mode:"width",value:size}}).render().asPng();
  return "image/png;base64,"+png.toString("base64");
}
const ICONS={};
function preload(){
  const need=["FaUserTie","FaWarehouse","FaFax","FaTruck","FaLaptopCode","FaDatabase","FaSitemap",
    "FaSearch","FaBell","FaExchangeAlt","FaCubes","FaServer","FaTable","FaClipboardList","FaBoxes",
    "FaHistory","FaCalculator","FaListUl","FaHospital","FaFileAlt","FaRoute","FaLightbulb"];
  for(const n of need) ICONS[n]=iconData(n,"#FFFFFF",256);
  ICONS["FaArrowRight"]=iconData("FaArrowRight",`#${MINT}`,256);
  ICONS["FaCheckCircle"]=iconData("FaCheckCircle",`#${MINT}`,256);
  ICONS["FaTimesCircle"]=iconData("FaTimesCircle",`#${OLD}`,256);
}

const pres=new pptxgen();
pres.defineLayout({name:"W",width:W,height:H});
pres.layout="W";
pres.author="Sascha Schulz";
pres.title="Lagerverwaltung - Projektpraesentation";

const mkShadow=()=>({type:"outer",color:"0B3C49",blur:9,offset:3,angle:90,opacity:0.16});

// ---------- helpers ----------
function chip(s,x,y,txt,fill,col){
  s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x,y,w:txt.w||2.2,h:0.42,fill:{color:fill},rectRadius:0.21,line:{type:"none"}});
  s.addText(txt.t,{x,y,w:txt.w||2.2,h:0.42,align:"center",valign:"middle",fontFace:"Calibri",fontSize:12.5,bold:true,color:col||"FFFFFF",margin:0});
}
function iconCircle(s,x,y,d,ic,fill){
  s.addShape(pres.shapes.OVAL,{x,y,w:d,h:d,fill:{color:fill},line:{type:"none"},shadow:mkShadow()});
  s.addImage({data:ICONS[ic],x:x+d*0.24,y:y+d*0.24,w:d*0.52,h:d*0.52});
}
function title(s,t,sub){
  s.addText(t,{x:0.7,y:0.5,w:11.9,h:0.7,fontFace:"Georgia",fontSize:30,bold:true,color:INK,margin:0});
  if(sub) s.addText(sub,{x:0.72,y:1.18,w:11.9,h:0.4,fontFace:"Calibri",fontSize:15,italic:true,color:TEAL2,margin:0});
}
function parallel(s,damals,heute){
  const y=6.78;
  s.addShape(pres.shapes.RECTANGLE,{x:0,y:y,w:W,h:0.72,fill:{color:DARK},line:{type:"none"}});
  s.addShape(pres.shapes.OVAL,{x:0.55,y:y+0.18,w:0.36,h:0.36,fill:{color:OLD},line:{type:"none"}});
  s.addImage({data:ICONS["FaWarehouse"],x:0.63,y:y+0.26,w:0.2,h:0.2});
  s.addText([{text:"DAMALS  ",options:{bold:true,color:OLDSOFT}},{text:damals,options:{color:"E8F1F2"}}],
    {x:1.0,y:y,w:5.4,h:0.72,valign:"middle",fontFace:"Calibri",fontSize:11.5,margin:0});
  s.addImage({data:ICONS["FaArrowRight"],x:6.42,y:y+0.26,w:0.26,h:0.2});
  s.addShape(pres.shapes.OVAL,{x:6.85,y:y+0.18,w:0.36,h:0.36,fill:{color:MINT},line:{type:"none"}});
  s.addImage({data:ICONS["FaLaptopCode"],x:6.93,y:y+0.26,w:0.2,h:0.2});
  s.addText([{text:"HEUTE  ",options:{bold:true,color:MINT}},{text:heute,options:{color:"E8F1F2"}}],
    {x:7.3,y:y,w:5.7,h:0.72,valign:"middle",fontFace:"Calibri",fontSize:11.5,margin:0});
}
function stat(s,x,y,w,num,lab,col){
  s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x,y,w,h:1.5,fill:{color:CARD},line:{color:LINE,width:1},rectRadius:0.1,shadow:mkShadow()});
  s.addShape(pres.shapes.RECTANGLE,{x,y,w:0.09,h:1.5,fill:{color:col},line:{type:"none"}});
  s.addText(num,{x:x+0.12,y:y+0.18,w:w-0.24,h:0.72,align:"center",fontFace:"Georgia",fontSize:28,bold:true,color:col,margin:0});
  s.addText(lab,{x:x+0.15,y:y+0.95,w:w-0.3,h:0.45,align:"center",valign:"top",fontFace:"Calibri",fontSize:11.5,color:MUTE,margin:0});
}
// three-stage journey: Zettel -> Amondis -> Heute
function journey(s,t,sub,stages,takeaway){
  s.background={path:"assets/bg_light.png"};
  title(s,t,sub);
  const cw=3.7, gap=0.39, y=1.95, ch=3.35;
  const xs=[0.72,0.72+cw+gap,0.72+2*(cw+gap)];
  stages.forEach((st,i)=>{
    const x=xs[i], col=st.col;
    s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x,y,w:cw,h:ch,fill:{color:CARD},line:{color:LINE,width:1},rectRadius:0.1,shadow:mkShadow()});
    s.addShape(pres.shapes.RECTANGLE,{x,y,w:cw,h:0.12,fill:{color:col},line:{type:"none"}});
    chip(s,x+(cw-1.6)/2,y+0.32,{t:st.stage,w:1.6},col,"FFFFFF");
    iconCircle(s,x+cw/2-0.46,y+0.98,0.92,st.ic,col);
    s.addText(st.head,{x:x+0.15,y:y+2.08,w:cw-0.3,h:0.42,align:"center",fontFace:"Calibri",fontSize:14.5,bold:true,color:INK,margin:0});
    s.addText(st.txt,{x:x+0.22,y:y+2.5,w:cw-0.44,h:0.78,align:"center",fontFace:"Calibri",fontSize:11.5,color:MUTE,margin:0,lineSpacingMultiple:1.06});
    if(i<2) s.addImage({data:ICONS["FaArrowRight"],x:x+cw+0.06,y:y+ch/2-0.12,w:0.26,h:0.2});
  });
  s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x:0.72,y:5.62,w:11.9,h:0.95,fill:{color:DARK},line:{type:"none"},rectRadius:0.1,shadow:mkShadow()});
  s.addImage({data:ICONS["FaRoute"],x:1.02,y:5.96,w:0.3,h:0.3});
  s.addText(takeaway,{x:1.55,y:5.62,w:10.85,h:0.95,valign:"middle",fontFace:"Calibri",fontSize:13.5,italic:true,color:"E8F4F2",margin:0});
}

(async()=>{
preload();

// ===== 1 TITLE =====
let s=pres.addSlide(); s.background={path:"assets/bg_dark.png"};
chip(s,0.7,0.7,{t:"SRH · Wirtschaftsinformatik · Gruppe 2551",w:4.4},"15505C","CFEFE9");
s.addText("Von Hand zu digital –",{x:0.7,y:1.85,w:12,h:0.9,fontFace:"Georgia",fontSize:40,bold:true,color:"FFFFFF",margin:0});
s.addText([{text:"und jetzt auf der ",options:{color:"FFFFFF"}},{text:"anderen Seite",options:{color:MINT,italic:true}},{text:" der Macht.",options:{color:"FFFFFF"}}],
  {x:0.7,y:2.62,w:12,h:0.9,fontFace:"Georgia",fontSize:40,bold:true,margin:0});
s.addShape(pres.shapes.RECTANGLE,{x:0.74,y:3.78,w:1.5,h:0.05,fill:{color:MINT},line:{type:"none"}});
s.addText("Entwicklung einer Software zur Lagerverwaltung von medizinischem Verbrauchsmaterial im Krankenhaus",
  {x:0.7,y:4.0,w:11.4,h:0.8,fontFace:"Calibri",fontSize:18,color:"D7E7E9",margin:0});
iconCircle(s,0.7,5.45,0.7,"FaUserTie",TEAL2);
s.addText([{text:"Sascha Schulz",options:{bold:true,color:"FFFFFF",breakLine:true,fontSize:16}},
  {text:"Wirtschaftsinformatiker · Betreuerin: Frau Cramer · Java + JavaFX + MySQL",options:{color:"AFCBCF",fontSize:12.5}}],
  {x:1.55,y:5.45,w:10,h:0.8,valign:"middle",fontFace:"Calibri",margin:0});

// ===== 2 WER SPRICHT =====
s=pres.addSlide(); s.background={path:"assets/bg_light.png"};
title(s,"Wer hier spricht","Drei Jahre Teamleiter im Zentrallager – belegt im Arbeitszeugnis");
s.addText([
 {text:"Bevor ich Software entwickelt habe, habe ich Lager ",options:{}},
 {text:"geführt",options:{bold:true,color:TEAL2}},
 {text:". Zuletzt als Teamleiter im Zentrallager der Diakonissen Speyer (02/2020–03/2023) – genau das Umfeld, das dieses Projekt im Kleinen nachbildet.",options:{}}],
 {x:0.72,y:1.75,w:8.0,h:1.3,fontFace:"Calibri",fontSize:15,color:INK,lineSpacingMultiple:1.1,margin:0});
iconCircle(s,9.2,1.7,1.1,"FaHospital",TEAL);
s.addText("Diakonissen Speyer",{x:8.4,y:2.85,w:2.7,h:0.3,align:"center",fontFace:"Calibri",fontSize:11,italic:true,color:MUTE,margin:0});
stat(s,0.72,3.5,2.85,"1.200","lagergeführte Artikel",TEAL2);
stat(s,3.79,3.5,2.85,"24.000+","Artikel über den Einkauf",TEAL);
stat(s,6.86,3.5,2.85,"15.000","Lieferscheine / Jahr",MINT);
stat(s,9.93,3.5,2.7,"12","Mitarbeiter geführt",OLD);
s.addText("Zahlen aus der beruflichen Tätigkeit; Warenwirtschaft im Unternehmen: mySAP ERP / Modul MM (laut Arbeitszeugnis Diakonissen Speyer mbH).",
 {x:0.72,y:5.25,w:11.9,h:0.4,fontFace:"Calibri",fontSize:11,italic:true,color:MUTE,margin:0});
parallel(s,"Anwender & Lagerleiter im SAP-Umfeld","derselbe Prozess, selbst programmiert");

// ===== 3 DER ROTE FADEN =====
s=pres.addSlide(); s.background={path:"assets/bg_dark.png"};
s.addText("Der rote Faden",{x:0.7,y:0.55,w:12,h:0.7,fontFace:"Georgia",fontSize:30,bold:true,color:"FFFFFF",margin:0});
s.addText("Dieselbe Reise an drei Stationen – einmal als Anwender, jetzt als Entwickler.",{x:0.72,y:1.25,w:12,h:0.4,fontFace:"Calibri",fontSize:15,italic:true,color:MINT,margin:0});
const stations=[
 ["FaFileAlt","ZETTEL","Das Problem","Handschrift, Fax, Excel – fehleranfällig, kein Überblick.",OLD],
 ["FaUserTie","AMONDIS","Damals · Anwender","Software federführend angepasst, zugeschnitten, eingeführt.",TEAL],
 ["FaLaptopCode","EIGENE SOFTWARE","Heute · Entwickler","Dieselbe Logik – diesmal selbst entworfen und gebaut.",MINT]];
const sxs=[0.9,4.92,8.94];
stations.forEach((st,i)=>{
 const x=sxs[i];
 s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x,y:2.1,w:3.5,h:2.65,fill:{color:"103F4C"},line:{color:st[4],width:1.5},rectRadius:0.12,shadow:mkShadow()});
 iconCircle(s,x+3.5/2-0.47,2.42,0.94,st[0],st[4]);
 s.addText(st[1],{x:x+0.1,y:3.55,w:3.3,h:0.32,align:"center",fontFace:"Calibri",fontSize:12.5,bold:true,spc:200,color:st[4],margin:0});
 s.addText(st[2],{x:x+0.1,y:3.86,w:3.3,h:0.36,align:"center",fontFace:"Georgia",fontSize:16,bold:true,color:"FFFFFF",margin:0});
 s.addText(st[3],{x:x+0.2,y:4.24,w:3.1,h:0.5,align:"center",fontFace:"Calibri",fontSize:11,color:"BFD7DA",margin:0,lineSpacingMultiple:1.05});
 if(i<2) s.addImage({data:ICONS["FaArrowRight"],x:x+3.55,y:3.25,w:0.3,h:0.24});
});
s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x:0.9,y:5.05,w:11.53,h:1.25,fill:{color:"0A2F39"},line:{color:TEAL2,width:1},rectRadius:0.12,shadow:mkShadow()});
iconCircle(s,1.2,5.32,0.7,"FaExchangeAlt",TEAL2);
s.addText([
 {text:"Mein Amondis-Projekt: ",options:{bold:true,color:MINT}},
 {text:"Ich habe die automatische Trennung von ~1.200 lagergeführten und 24.000+ Einkaufs-Artikeln eingeführt – vorher nicht möglich. Genau diese Denkweise bringe ich jetzt als Entwickler ein.",options:{color:"CFE3E6"}}],
 {x:2.1,y:5.1,w:10.1,h:1.15,valign:"middle",fontFace:"Calibri",fontSize:13,margin:0,lineSpacingMultiple:1.1});

// ===== 4 JOURNEY: BESTELLEN =====
s=pres.addSlide();
journey(s,"Bestellen","Vom Fax-Zettel zur eigenen Bestellverwaltung",[
 {stage:"ZETTEL",col:OLD,ic:"FaFax",head:"Anforderung per Fax",txt:"Stationen & 13 Außenhäuser füllen Zettel aus – Fax/Mail ans Zentrallager."},
 {stage:"AMONDIS",col:TEAL,ic:"FaUserTie",head:"Anwender: digital bestellt",txt:"Jeder Standort bestellt selbst in Amondis – von mir eingeführt."},
 {stage:"HEUTE",col:MINT,ic:"FaLaptopCode",head:"Selbst programmiert",txt:"Bestellverwaltung mit Status – CRUD in meiner Java-Anwendung."}],
 "Beschaffung: vom handschriftlichen Zettel zur selbst gebauten Bestellverwaltung.");

// ===== 5 JOURNEY: BESTAND & LISTEN =====
s=pres.addSlide();
journey(s,"Bestand & Listen","Von der Excel-Liste zum berechneten Bestand",[
 {stage:"ZETTEL",col:OLD,ic:"FaFileAlt",head:"Excel & Papier",txt:"Bestände in Listen geführt – fehleranfällig und nie wirklich aktuell."},
 {stage:"AMONDIS",col:TEAL,ic:"FaDatabase",head:"Anwender: zentrale DB",txt:"Ein Systembestand für alle Standorte – einmal gepflegt, überall gültig."},
 {stage:"HEUTE",col:MINT,ic:"FaCalculator",head:"Selbst programmiert",txt:"Bestand wird live aus den Bewegungen berechnet (SQL-Aggregation)."}],
 "Bestände: von der fehleranfälligen Excel-Liste zum live berechneten Bestand.");

// ===== 6 JOURNEY: UEBERBLICK & WARNUNG =====
s=pres.addSlide();
journey(s,"Überblick & Warnung","Vom blinden Fleck zur automatischen Warnung",[
 {stage:"ZETTEL",col:OLD,ic:"FaBell",head:"Kein Warnsystem",txt:"Ein Engpass fällt erst auf, wenn das Material bereits fehlt."},
 {stage:"AMONDIS",col:TEAL,ic:"FaUserTie",head:"Anwender: Mindestbestände",txt:"Im System hinterlegt, Bestände jederzeit sichtbar."},
 {stage:"HEUTE",col:MINT,ic:"FaLaptopCode",head:"Selbst programmiert",txt:"Automatische Mindestbestand-Warnung – kritische Bestände in Rot."}],
 "Überblick: vom blinden Fleck zur automatischen Mindestbestand-Warnung.");

// ===== 7 JOURNEY: NACHVOLLZIEHBARKEIT =====
s=pres.addSlide();
journey(s,"Nachvollziehbarkeit","Vom Gedächtnis zur lückenlosen Historie",[
 {stage:"ZETTEL",col:OLD,ic:"FaFileAlt",head:"Keine Historie",txt:"Zu- und Abgänge sind nirgends nachvollziehbar dokumentiert."},
 {stage:"AMONDIS",col:TEAL,ic:"FaDatabase",head:"Anwender: Systembuchung",txt:"Bewegungen werden im System erfasst und sind nachvollziehbar."},
 {stage:"HEUTE",col:MINT,ic:"FaHistory",head:"Selbst programmiert",txt:"Lückenlose Bewegungs-Historie in der Tabelle bestandsbewegungen."}],
 "Nachvollziehbarkeit: vom Gedächtnis zur lückenlosen Bewegungs-Historie.");

// ===== 8 TECHNIK KOMPAKT (Pflicht + Architektur) =====
s=pres.addSlide(); s.background={path:"assets/bg_light.png"};
title(s,"Die Technik dahinter","SRH-Pflichtkriterien – umgesetzt in MVC + DAO · Java 17 · JavaFX · MySQL 8");
const reqs=[["MySQL · 6 Tabellen","mind. 5 gefordert (3NF)",TEAL],
 ["CRUD","alle Stammdaten",TEAL2],
 ["MVC + DAO","Daten · Logik · UI getrennt",MINT],
 ["Suche & Filter","Material sofort finden",OLD]];
reqs.forEach((r,i)=>{
 const x=0.72+i*3.02;
 s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x,y:1.75,w:2.85,h:1.18,fill:{color:CARD},line:{color:LINE,width:1},rectRadius:0.1,shadow:mkShadow()});
 s.addShape(pres.shapes.RECTANGLE,{x,y:1.75,w:0.09,h:1.18,fill:{color:r[2]},line:{type:"none"}});
 s.addImage({data:ICONS["FaCheckCircle"],x:x+0.25,y:1.95,w:0.26,h:0.26});
 s.addText(r[0],{x:x+0.6,y:1.9,w:2.15,h:0.4,fontFace:"Calibri",fontSize:13.5,bold:true,color:INK,margin:0});
 s.addText(r[1],{x:x+0.25,y:2.4,w:2.5,h:0.4,fontFace:"Calibri",fontSize:11,color:MUTE,margin:0});
});
const layers=[["FaTable","View","main.fxml + Controller",TEAL],
 ["FaCubes","Model","6 POJO-Klassen",TEAL2],
 ["FaServer","DAO","CRUD via PreparedStatement",MINT],
 ["FaDatabase","Datenbank","MySQL · krankenhaus_lager",OLD]];
layers.forEach((l,i)=>{
 const x=0.72+i*3.04;
 s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x,y:3.35,w:2.78,h:2.45,fill:{color:CARD},line:{color:LINE,width:1},rectRadius:0.1,shadow:mkShadow()});
 s.addShape(pres.shapes.RECTANGLE,{x,y:3.35,w:2.78,h:0.12,fill:{color:l[3]},line:{type:"none"}});
 iconCircle(s,x+2.78/2-0.42,3.65,0.84,l[0],l[3]);
 s.addText(l[1],{x:x+0.1,y:4.62,w:2.58,h:0.4,align:"center",fontFace:"Calibri",fontSize:16,bold:true,color:INK,margin:0});
 s.addText(l[2],{x:x+0.12,y:5.04,w:2.54,h:0.7,align:"center",fontFace:"Calibri",fontSize:11,color:MUTE,margin:0,lineSpacingMultiple:1.05});
 if(i<layers.length-1) s.addImage({data:ICONS["FaArrowRight"],x:x+2.79,y:4.35,w:0.22,h:0.2});
});
parallel(s,"SAP/Amondis als fertige Großsysteme","eigene Schichten: View · Model · DAO · DB");

// ===== 9 DATENMODELL =====
s=pres.addSlide(); s.background={path:"assets/bg_light.png"};
title(s,"Das Datenmodell","6 Tabellen in 3. Normalform – das Herz der Anwendung");
s.addImage({path:"assets/er.png",x:0.6,y:1.65,w:8.3,h:4.78,sizing:{type:"contain",w:8.3,h:4.78}});
s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x:9.15,y:1.75,w:3.5,h:4.55,fill:{color:CARD},line:{color:LINE,width:1},rectRadius:0.1,shadow:mkShadow()});
s.addText("Die 6 Tabellen",{x:9.4,y:1.95,w:3.0,h:0.4,fontFace:"Calibri",fontSize:14,bold:true,color:TEAL2,margin:0});
[["kategorien","Materialgruppen"],["materialien","Artikelstammdaten"],["stationslager","Lagerorte / Stationen"],
 ["lieferanten","Bezugsquellen"],["bestellungen","offene Bestellungen"],["bestandsbewegungen","Ein-/Ausgänge"]].forEach((t,i)=>{
 const y=2.45+i*0.62;
 s.addShape(pres.shapes.OVAL,{x:9.4,y:y+0.04,w:0.16,h:0.16,fill:{color:MINT},line:{type:"none"}});
 s.addText([{text:t[0]+"  ",options:{bold:true,color:INK,fontSize:12.5}},{text:t[1],options:{color:MUTE,fontSize:11}}],
  {x:9.66,y:y-0.06,w:2.85,h:0.5,fontFace:"Calibri",valign:"top",margin:0});
});
parallel(s,"Tausende Artikel in SAP-Stammdaten gepflegt","Stammdaten selbst modelliert & normalisiert");

// ===== 10 DESIGNENTSCHEIDUNG =====
s=pres.addSlide(); s.background={path:"assets/bg_dark.png"};
s.addText("Designentscheidung im Detail",{x:0.7,y:0.55,w:12,h:0.7,fontFace:"Georgia",fontSize:28,bold:true,color:"FFFFFF",margin:0});
s.addText("Der Bestand wird nicht gespeichert – er wird berechnet.",{x:0.72,y:1.25,w:12,h:0.4,fontFace:"Calibri",fontSize:15,italic:true,color:MINT,margin:0});
s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x:1.2,y:2.2,w:10.9,h:1.5,fill:{color:"0A2F39"},line:{color:MINT,width:1.5},rectRadius:0.12,shadow:mkShadow()});
s.addText([{text:"Bestand",options:{color:MINT,bold:true}},{text:"  =  Σ EINGANG  −  Σ AUSGANG",options:{color:"FFFFFF"}}],
 {x:1.2,y:2.2,w:10.9,h:1.5,align:"center",valign:"middle",fontFace:"Consolas",fontSize:26,bold:true,margin:0});
s.addText([
 {text:"Warum? ",options:{bold:true,color:"FFFFFF"}},
 {text:"Jede Buchung ist ein Datensatz in ",options:{color:"CFE3E6"}},
 {text:"bestandsbewegungen",options:{color:MINT,bold:true}},
 {text:". Der aktuelle Bestand entsteht per SQL-Aggregation (SUM / CASE WHEN). Das verhindert widersprüchliche Werte und liefert kostenlos eine lückenlose Historie – dasselbe Prinzip wie die bestandsgeführte Logik in SAP.",options:{color:"CFE3E6"}}],
 {x:1.2,y:4.0,w:10.9,h:1.6,fontFace:"Calibri",fontSize:15,margin:0,lineSpacingMultiple:1.2});
s.addText("→ Eine bewusste Entscheidung, die ich im Fachgespräch begründen kann.",
 {x:1.2,y:5.75,w:10.9,h:0.4,fontFace:"Calibri",fontSize:13,italic:true,color:MINT,margin:0});

// ===== 11 FAZIT =====
s=pres.addSlide(); s.background={path:"assets/bg_dark.png"};
chip(s,0.7,0.7,{t:"FAZIT",w:1.3},MINT,"0B3C49");
s.addText("Vom Zettel in die Moderne",{x:0.7,y:1.7,w:12,h:0.9,fontFace:"Georgia",fontSize:36,bold:true,color:"FFFFFF",margin:0});
s.addText([
 {text:"Ich kenne die Zettelwirtschaft aus dem echten Lageralltag, habe sie als ",options:{color:"D7E7E9"}},
 {text:"Anwender",options:{color:OLDSOFT,bold:true}},
 {text:" mit Amondis digitalisiert – und stehe heute als ",options:{color:"D7E7E9"}},
 {text:"Entwickler",options:{color:MINT,bold:true}},
 {text:" auf der anderen Seite der Macht.",options:{color:"D7E7E9"}}],
 {x:0.7,y:2.75,w:11.8,h:0.9,fontFace:"Calibri",fontSize:18,margin:0,lineSpacingMultiple:1.15});
const tk=[["FaFileAlt","Zettel","Das Problem aus erster Hand erlebt.",OLD],
 ["FaUserTie","Anwender","Mit Amondis federführend digitalisiert.",TEAL],
 ["FaLaptopCode","Entwickler","Dieselbe Lösung selbst gebaut.",MINT]];
tk.forEach((t,i)=>{
 const x=0.9+i*3.95;
 s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x,y:3.95,w:3.6,h:1.95,fill:{color:"103F4C"},line:{color:t[3],width:1.2},rectRadius:0.12,shadow:mkShadow()});
 iconCircle(s,x+0.28,4.2,0.62,t[0],t[3]);
 s.addText(t[1],{x:x+1.05,y:4.25,w:2.4,h:0.6,fontFace:"Calibri",fontSize:15,bold:true,color:"FFFFFF",margin:0,valign:"middle"});
 s.addText(t[2],{x:x+0.28,y:4.95,w:3.05,h:0.8,fontFace:"Calibri",fontSize:11.5,color:"BFD7DA",margin:0,lineSpacingMultiple:1.05});
 if(i<2) s.addImage({data:ICONS["FaArrowRight"],x:x+3.62,y:4.78,w:0.26,h:0.2});
});
s.addText("Vielen Dank – ich freue mich auf Ihre Fragen.",{x:0.7,y:6.25,w:12,h:0.5,align:"center",fontFace:"Georgia",fontSize:18,italic:true,color:MINT,margin:0});

// ---------- Speaker notes ----------
const notes=[
"Begrüßung. Titel nennen. Aufhänger: Diese Präsentation hat zwei Ebenen – mein realer Berufshintergrund im Krankenhauslager und das Softwareprojekt. Der Titel spielt augenzwinkernd auf Star Wars an: Ich stehe heute auf der anderen Seite – als Entwickler.",
"Glaubwürdigkeit: 3 Jahre Teamleiter im Zentrallager der Diakonissen Speyer. Zahlen: rund 1.200 lagergeführte Artikel, über 24.000 Artikel über den Einkauf, 15.000 Lieferscheine/Jahr, 12 Mitarbeiter. Warenwirtschaft mySAP ERP, Modul MM.",
"Roter Faden / Leitidee der Präsentation: Jedes Problem zeige ich an drei Stationen – Zettel (Problem), Amondis (damals, als Anwender gelöst), eigene Software (heute, als Entwickler). Mein Amondis-Highlight: die automatische Trennung von Lager- und Einkaufs-Artikeln, vorher nicht möglich.",
"Bestellen: Früher Anforderungszettel per Fax. Mit Amondis bestellte jeder Standort selbst digital (von mir eingeführt). Heute: eigene Bestellverwaltung mit Status, CRUD in Java.",
"Bestand & Listen: Früher Excel/Papier, nie aktuell. Amondis brachte einen zentralen Systembestand. Heute berechne ich den Bestand live aus den Bewegungen per SQL.",
"Überblick & Warnung: Früher kein Warnsystem – Engpass fiel zu spät auf. Amondis: Mindestbestände im System. Heute: automatische Mindestbestand-Warnung, kritische Bestände rot.",
"Nachvollziehbarkeit: Früher keine Historie. Amondis: Systembuchungen. Heute: lückenlose Bewegungs-Historie in bestandsbewegungen.",
"Technik kompakt: SRH-Pflicht erfüllt – MySQL mit 6 Tabellen (statt 5), CRUD, MVC, Suche/Filter. Architektur: View → Model → DAO → DB. Details gern im Fachgespräch.",
"ER-Diagramm: 6 Tabellen in 3. Normalform, Fremdschlüssel kurz erklären. Früher Stammdaten in SAP gepflegt, jetzt selbst modelliert.",
"Designentscheidung: Bestand wird nicht gespeichert, sondern aus den Bewegungen berechnet (SUM EINGANG − AUSGANG). Vorteile: keine Widersprüche, kostenlose Historie. Im Fachgespräch begründbar.",
"Fazit, Bogen schließen: Vom Zettel in die Moderne. Ich stand auf beiden Seiten – Anwender und Entwickler. Dank und Überleitung zu Fragen."
];
pres.slides.forEach((sl,i)=>{ if(notes[i]) sl.addNotes(notes[i]); });

await pres.writeFile({fileName:"Praesentation_Lagerverwaltung_Sascha_Schulz.pptx"});
console.log("PPTX geschrieben:", pres.slides.length, "Folien");
})();
