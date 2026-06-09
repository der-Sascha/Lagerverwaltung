const pptxgen = require("pptxgenjs");
const React = require("react");
const ReactDOMServer = require("react-dom/server");
const sharp = require("sharp");
const FA = require("react-icons/fa");

// ---------- Palette ----------
const INK="12303A", DARK="0B3C49", TEAL="087E8B", TEAL2="028090", MINT="02C39A";
const OLD="E76F51", OLDSOFT="F4A98F", CREAM="F6F8F8", CARD="FFFFFF", MUTE="5B7179", LINE="D9E2E4";
const W=13.333, H=7.5;

// ---------- Icon rasterizer ----------
async function icon(name, color="#FFFFFF", size=256){
  const Comp = FA[name];
  const svg = ReactDOMServer.renderToStaticMarkup(React.createElement(Comp,{color,size:String(size)}));
  const png = await sharp(Buffer.from(svg)).png().toBuffer();
  return "image/png;base64,"+png.toString("base64");
}
const ICONS={};
async function preload(){
  const need=["FaUserTie","FaWarehouse","FaFax","FaTruck","FaLaptopCode","FaDatabase","FaSitemap",
    "FaSearch","FaBell","FaExchangeAlt","FaCode","FaBug","FaGraduationCap","FaCubes","FaServer","FaTable",
    "FaFlask","FaProjectDiagram","FaClipboardList","FaBarcode","FaLightbulb","FaCalculator","FaBoxOpen",
    "FaHospital","FaPlug","FaListUl"];
  for(const n of need) ICONS[n]=await icon(n,"#FFFFFF",256);
  ICONS["FaArrowRight"]=await icon("FaArrowRight",`#${MINT}`,256);
  ICONS["FaCheckCircle"]=await icon("FaCheckCircle",`#${MINT}`,256);
  ICONS["FaTimesCircle"]=await icon("FaTimesCircle",`#${OLD}`,256);
}

const pres=new pptxgen();
pres.defineLayout({name:"W",width:W,height:H});
pres.layout="W";
pres.author="Sascha Schulz";
pres.title="Lagerverwaltung – Projektpräsentation";

const mkShadow=()=>({type:"outer",color:"0B3C49",blur:9,offset:3,angle:90,opacity:0.16});

// ---------- reusable helpers ----------
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

(async()=>{
await preload();

// ===== 1 TITLE =====
let s=pres.addSlide(); s.background={path:"assets/bg_dark.png"};
chip(s,0.7,0.7,{t:"SRH · Wirtschaftsinformatik · Gruppe 2551",w:4.4},"15505C","CFEFE9");
s.addText("Von Hand zu digital –",{x:0.7,y:1.85,w:12,h:0.9,fontFace:"Georgia",fontSize:40,bold:true,color:"FFFFFF",margin:0});
s.addText([{text:"und jetzt auf der ",options:{color:"FFFFFF"}},{text:"anderen Seite",options:{color:MINT,italic:true}},{text:" des Tisches.",options:{color:"FFFFFF"}}],
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

// ===== 3 AUSGANGSLAGE DAMALS =====
s=pres.addSlide(); s.background={path:"assets/bg_light.png"};
title(s,"Die Ausgangslage – damals","Nur das Zentrallager hatte ein System. Der Rest lief auf Papier.");
const steps=[
 ["FaClipboardList","Station / Außenhaus","Anforderungszettel\nhandschriftlich",OLD],
 ["FaFax","Fax & E-Mail","Zettel ans\nZentrallager",OLD],
 ["FaDatabase","SAP-Eingabe","manuell ins System\ngetippt",TEAL],
 ["FaListUl","Kommissionierliste","erstellt &\nbearbeitet",TEAL],
 ["FaTruck","Fahrdienst","Ware ausgeliefert\n/ gefahren",TEAL2]];
let bx=0.72, bw=2.18, gap=0.22, by=2.1;
steps.forEach((st,i)=>{
 const x=bx+i*(bw+gap);
 s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x,y:by,w:bw,h:2.5,fill:{color:CARD},line:{color:LINE,width:1},rectRadius:0.1,shadow:mkShadow()});
 iconCircle(s,x+bw/2-0.4,by+0.25,0.8,st[0],st[3]);
 s.addText(st[1],{x:x+0.1,y:by+1.18,w:bw-0.2,h:0.45,align:"center",fontFace:"Calibri",fontSize:13,bold:true,color:INK,margin:0});
 s.addText(st[2],{x:x+0.1,y:by+1.62,w:bw-0.2,h:0.75,align:"center",fontFace:"Calibri",fontSize:11.5,color:MUTE,margin:0});
 if(i<steps.length-1) s.addImage({data:ICONS["FaArrowRight"],x:x+bw+0.01,y:by+1.05,w:0.2,h:0.2});
});
s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x:0.72,y:4.95,w:11.9,h:1.45,fill:{color:"FDEEE8"},line:{color:OLDSOFT,width:1},rectRadius:0.1});
s.addImage({data:ICONS["FaTimesCircle"],x:0.95,y:5.18,w:0.3,h:0.3});
s.addText("Die Schwachstellen",{x:1.35,y:5.12,w:4,h:0.4,fontFace:"Calibri",fontSize:14,bold:true,color:OLD,margin:0});
s.addText("Medienbruch Papier→System  ·  Tippfehler & Doppelerfassung  ·  keine Echtzeit-Bestände  ·  jede Bestellung einzeln  ·  Stationen ohne Systemzugang",
 {x:1.35,y:5.55,w:11.0,h:0.75,fontFace:"Calibri",fontSize:13,color:"8A3B28",lineSpacingMultiple:1.1,margin:0});
parallel(s,"13 Außenhäuser + Stationen bestellen per Fax","jeder Standort bestellt direkt im System");

// ===== 4 DIE WENDE: AMONDIS =====
s=pres.addSlide(); s.background={path:"assets/bg_light.png"};
title(s,"Die Wende: Pilotprojekt Amondis","Federführend bei Anpassung, Zuschnitt und Einführung der Software");
s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x:0.72,y:1.7,w:5.55,h:2.85,fill:{color:CARD},line:{color:OLDSOFT,width:1.5},rectRadius:0.12,shadow:mkShadow()});
chip(s,0.95,1.88,{t:"VORHER",w:1.4},OLD,"FFFFFF");
s.addText([
 {text:"Insel-Lösung",options:{bold:true,color:INK,fontSize:15,breakLine:true}},
 {text:"Nur das Zentrallager arbeitete im SAP. Stationen & Außenhäuser schickten Zettel – zentral abgetippt.",options:{color:MUTE,fontSize:12.5}}],
 {x:0.95,y:2.45,w:5.1,h:0.9,fontFace:"Calibri",margin:0,lineSpacingMultiple:1.05});
["Manuelle Erfassung im Zentrallager","Bestellungen einzeln & unverbunden","kein direkter Standort-Zugriff"].forEach((t,i)=>{
 s.addImage({data:ICONS["FaTimesCircle"],x:0.98,y:3.4+i*0.34,w:0.22,h:0.22});
 s.addText(t,{x:1.3,y:3.35+i*0.34,w:4.8,h:0.32,fontFace:"Calibri",fontSize:12,color:INK,valign:"middle",margin:0});
});
iconCircle(s,6.42,2.85,0.6,"FaArrowRight",TEAL2);
s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x:7.1,y:1.7,w:5.52,h:2.85,fill:{color:"E9FBF4"},line:{color:MINT,width:1.5},rectRadius:0.12,shadow:mkShadow()});
chip(s,7.33,1.88,{t:"NACHHER",w:1.5},MINT,"FFFFFF");
s.addText([
 {text:"Voll digitaler Ablauf",options:{bold:true,color:INK,fontSize:15,breakLine:true}},
 {text:"Zentrallager, alle Stationen und jedes Außenhaus erhielten Amondis – passend zugeschnitten, bestellen direkt.",options:{color:"2C6B5B",fontSize:12.5}}],
 {x:7.33,y:2.45,w:5.05,h:0.9,fontFace:"Calibri",margin:0,lineSpacingMultiple:1.05});
["Jeder Standort bestellt selbst digital","Mehrfach-Bestellungen zusammengefasst","durchgängiger Prozess ohne Papier"].forEach((t,i)=>{
 s.addImage({data:ICONS["FaCheckCircle"],x:7.36,y:3.4+i*0.34,w:0.22,h:0.22});
 s.addText(t,{x:7.68,y:3.35+i*0.34,w:4.8,h:0.32,fontFace:"Calibri",fontSize:12,color:INK,valign:"middle",margin:0});
});
// automatic-separation callout (NEW)
s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x:0.72,y:4.78,w:11.9,h:1.62,fill:{color:DARK},line:{type:"none"},rectRadius:0.12,shadow:mkShadow()});
iconCircle(s,1.02,5.18,0.82,"FaExchangeAlt",TEAL2);
s.addText("Neue Funktion: automatische Trennung – vorher nicht möglich",
 {x:2.1,y:4.95,w:10.4,h:0.4,fontFace:"Calibri",fontSize:15,bold:true,color:MINT,margin:0});
s.addText([
 {text:"~1.200 lagergeführte Artikel",options:{bold:true,color:"FFFFFF"}},
 {text:" verbleiben im Lager  ·  ",options:{color:"CFE3E6"}},
 {text:"24.000+ Artikel",options:{bold:true,color:"FFFFFF"}},
 {text:" werden automatisch an den Einkauf zur Bestellung weitergeleitet. Das System trennt beide Wege selbstständig – diese Aufteilung war früher nicht möglich.",options:{color:"CFE3E6"}}],
 {x:2.1,y:5.4,w:10.4,h:0.95,fontFace:"Calibri",fontSize:13,margin:0,lineSpacingMultiple:1.12});
parallel(s,"Amondis anpassen & verteilen (Anwenderseite)","Lagersoftware selbst entwerfen & bauen");

// ===== 5 BRÜCKENSCHLAG =====
s=pres.addSlide(); s.background={path:"assets/bg_dark.png"};
s.addText("Der Brückenschlag",{x:0.7,y:0.6,w:12,h:0.7,fontFace:"Georgia",fontSize:30,bold:true,color:"FFFFFF",margin:0});
s.addText("Warum genau dieses Projekt?",{x:0.72,y:1.28,w:12,h:0.4,fontFace:"Calibri",fontSize:15,italic:true,color:MINT,margin:0});
s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x:0.9,y:2.3,w:4.3,h:2.7,fill:{color:"103F4C"},line:{color:OLD,width:1.5},rectRadius:0.12,shadow:mkShadow()});
iconCircle(s,1.25,2.65,0.95,"FaUserTie",OLD);
s.addText("DAMALS",{x:1.25,y:3.7,w:3.6,h:0.35,fontFace:"Calibri",fontSize:13,bold:true,color:OLDSOFT,charSpacing:2,margin:0});
s.addText("Anwender",{x:1.25,y:4.0,w:3.6,h:0.4,fontFace:"Georgia",fontSize:22,bold:true,color:"FFFFFF",margin:0});
s.addText("Ich habe die Software bedient, angepasst und eingeführt.",{x:1.25,y:4.45,w:3.7,h:0.5,fontFace:"Calibri",fontSize:12.5,color:"BFD7DA",margin:0});
iconCircle(s,6.1,3.15,1.1,"FaArrowRight",TEAL2);
s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x:8.1,y:2.3,w:4.3,h:2.7,fill:{color:"103F4C"},line:{color:MINT,width:1.5},rectRadius:0.12,shadow:mkShadow()});
iconCircle(s,8.45,2.65,0.95,"FaLaptopCode",MINT);
s.addText("HEUTE",{x:8.45,y:3.7,w:3.6,h:0.35,fontFace:"Calibri",fontSize:13,bold:true,color:MINT,charSpacing:2,margin:0});
s.addText("Entwickler",{x:8.45,y:4.0,w:3.6,h:0.4,fontFace:"Georgia",fontSize:22,bold:true,color:"FFFFFF",margin:0});
s.addText("Ich baue das System selbst – Datenmodell, Logik und Oberfläche.",{x:8.45,y:4.45,w:3.7,h:0.5,fontFace:"Calibri",fontSize:12.5,color:"BFD7DA",margin:0});
s.addText("„Ich kenne den Schmerz der Zettelwirtschaft aus erster Hand. Jetzt löse ich ihn als Entwickler – das ist meine Motivation für dieses Projekt.\"",
 {x:1.4,y:5.45,w:10.5,h:1.0,align:"center",fontFace:"Georgia",fontSize:17,italic:true,color:"E8F4F2",margin:0});

// ===== 6 DAS PROJEKT =====
s=pres.addSlide(); s.background={path:"assets/bg_light.png"};
title(s,"Das Projekt","Dieselbe Aufgabe – jetzt von der Entwicklerseite gelöst");
s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x:0.72,y:1.8,w:7.4,h:3.5,fill:{color:CARD},line:{color:LINE,width:1},rectRadius:0.12,shadow:mkShadow()});
const rows=[["FaHospital","Auftrag","Lagerverwaltung medizinischen Verbrauchsmaterials für ein (fiktives) Krankenhaus.",TEAL],
 ["FaProjectDiagram","Ziel","Manuelle Listen ablösen durch eine strukturierte Desktop-Anwendung mit Datenbank.",TEAL2],
 ["FaCubes","Umfang","CRUD über 6 Tabellen, Suche/Filter, Bestands- und Bestellverwaltung, lokal ohne Internet.",MINT]];
rows.forEach((r,i)=>{
 const y=2.05+i*1.05;
 iconCircle(s,0.95,y,0.72,r[0],r[3]);
 s.addText(r[1],{x:1.85,y:y-0.02,w:6.1,h:0.35,fontFace:"Calibri",fontSize:14,bold:true,color:INK,margin:0});
 s.addText(r[2],{x:1.85,y:y+0.3,w:6.1,h:0.65,fontFace:"Calibri",fontSize:12.5,color:MUTE,margin:0,lineSpacingMultiple:1.05});
});
s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x:8.35,y:1.8,w:4.27,h:3.5,fill:{color:DARK},line:{type:"none"},rectRadius:0.12,shadow:mkShadow()});
s.addText("Synonym zur Praxis",{x:8.6,y:2.05,w:3.8,h:0.4,fontFace:"Calibri",fontSize:14,bold:true,color:MINT,margin:0});
s.addText([
 {text:"Krankenhaus mit Lager",options:{color:"E8F4F2",breakLine:true,bold:true}},
 {text:"Verbrauchsmaterial verwalten",options:{color:"BFD7DA",fontSize:12,breakLine:true}},
 {text:"Bestand & Bestellungen",options:{color:"E8F4F2",breakLine:true,bold:true}},
 {text:"Mindestbestand statt ABC-Liste",options:{color:"BFD7DA",fontSize:12,breakLine:true}},
 {text:"Mehrere Lager / Stationen",options:{color:"E8F4F2",breakLine:true,bold:true}},
 {text:"wie Zentrallager + Außenhäuser",options:{color:"BFD7DA",fontSize:12}}],
 {x:8.6,y:2.55,w:3.8,h:2.6,fontFace:"Calibri",fontSize:13.5,margin:0,lineSpacingMultiple:1.15});
parallel(s,"reales Krankenhauslager mit SAP/Amondis","fiktives Lager – eigene Java-Anwendung");

// ===== 7 ANFORDERUNGEN =====
s=pres.addSlide(); s.background={path:"assets/bg_light.png"};
title(s,"Die Anforderungen","SRH-Pflichtkriterien – jede spiegelt eine reale Lager-Aufgabe");
const reqs=[["FaDatabase","MySQL-Datenbank","mind. 5 Tabellen → umgesetzt mit 6 Tabellen (3NF)",TEAL],
 ["FaListUl","CRUD-Funktionalität","Anlegen, Lesen, Ändern, Löschen aller Stammdaten",TEAL2],
 ["FaSitemap","MVC-Architektur","saubere Trennung von Daten, Logik und Oberfläche",MINT],
 ["FaSearch","Such- & Filterfunktion","Material schnell finden – wie die Kommissionierung",OLD]];
reqs.forEach((r,i)=>{
 const x=0.72+(i%2)*6.05, y=1.85+Math.floor(i/2)*1.9;
 s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x,y,w:5.85,h:1.65,fill:{color:CARD},line:{color:LINE,width:1},rectRadius:0.1,shadow:mkShadow()});
 s.addShape(pres.shapes.RECTANGLE,{x,y,w:0.09,h:1.65,fill:{color:r[3]},line:{type:"none"}});
 iconCircle(s,x+0.28,y+0.45,0.75,r[0],r[3]);
 s.addText(r[1],{x:x+1.2,y:y+0.3,w:4.5,h:0.4,fontFace:"Calibri",fontSize:15.5,bold:true,color:INK,margin:0});
 s.addText(r[2],{x:x+1.2,y:y+0.78,w:4.5,h:0.6,fontFace:"Calibri",fontSize:12.5,color:MUTE,margin:0,lineSpacingMultiple:1.05});
});
parallel(s,"SAP/Amondis erfüllten diese Aufgaben im Großen","selbe Bausteine – im Kleinen selbst gebaut");

// ===== 8 PROBLEM IM MODELL =====
s=pres.addSlide(); s.background={path:"assets/bg_light.png"};
title(s,"Das Problem im Modell","Die Ist-Analyse des Krankenhauses spiegelt die Insel-Situation von früher");
const probs=[["FaClipboardList","Manuelle Listen","Bestände in Excel/Papier – fehleranfällig und nie aktuell."],
 ["FaBell","Kein Warnsystem","Niemand merkt rechtzeitig, wenn ein Material zur Neige geht."],
 ["FaExchangeAlt","Keine Historie","Zu- und Abgänge sind nicht nachvollziehbar dokumentiert."]];
probs.forEach((p,i)=>{
 const x=0.72+i*4.0;
 s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x,y:1.95,w:3.75,h:2.5,fill:{color:CARD},line:{color:LINE,width:1},rectRadius:0.12,shadow:mkShadow()});
 iconCircle(s,x+0.3,2.25,0.78,p[0],OLD);
 s.addText(p[1],{x:x+0.25,y:3.2,w:3.3,h:0.4,fontFace:"Calibri",fontSize:15,bold:true,color:INK,margin:0});
 s.addText(p[2],{x:x+0.25,y:3.6,w:3.3,h:0.8,fontFace:"Calibri",fontSize:12.5,color:MUTE,margin:0,lineSpacingMultiple:1.1});
});
s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x:0.72,y:4.75,w:11.9,h:1.55,fill:{color:"E9FBF4"},line:{color:MINT,width:1},rectRadius:0.1});
s.addImage({data:ICONS["FaLightbulb"],x:0.98,y:5.0,w:0.32,h:0.32});
s.addText("Soll-Konzept",{x:1.4,y:4.95,w:4,h:0.4,fontFace:"Calibri",fontSize:14,bold:true,color:TEAL2,margin:0});
s.addText("Eine zentrale Anwendung mit Datenbank: jederzeit aktuelle Bestände, automatische Mindestbestand-Warnung und lückenlose Bewegungs-Historie – genau das, was Amondis im echten Lager geschaffen hat.",
 {x:1.4,y:5.35,w:11.0,h:0.9,fontFace:"Calibri",fontSize:13,color:"2C6B5B",margin:0,lineSpacingMultiple:1.1});
parallel(s,"Zettel, kein Warnsystem, keine Nachvollziehbarkeit","DB-gestützt, Warnungen, volle Historie");

// ===== 9 ARCHITEKTUR =====
s=pres.addSlide(); s.background={path:"assets/bg_light.png"};
title(s,"Die Lösung: Architektur","MVC + DAO-Muster · Java 17 · JavaFX · MySQL 8 – lokal, ohne Internet");
const layers=[["FaTable","View","main.fxml + Controller","Oberfläche: Tabellen, Suche, Dialoge",TEAL],
 ["FaCubes","Model","6 POJO-Klassen","spiegeln je eine Tabelle",TEAL2],
 ["FaServer","DAO","6 DAO-Klassen","CRUD via PreparedStatement",MINT],
 ["FaDatabase","Datenbank","MySQL · krankenhaus_lager","6 Tabellen, Port 3324",OLD]];
layers.forEach((l,i)=>{
 const x=0.72+i*3.04;
 s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x,y:2.0,w:2.78,h:2.7,fill:{color:CARD},line:{color:LINE,width:1},rectRadius:0.1,shadow:mkShadow()});
 s.addShape(pres.shapes.RECTANGLE,{x,y:2.0,w:2.78,h:0.12,fill:{color:l[4]},line:{type:"none"}});
 iconCircle(s,x+2.78/2-0.42,2.35,0.84,l[0],l[4]);
 s.addText(l[1],{x:x+0.1,y:3.32,w:2.58,h:0.4,align:"center",fontFace:"Calibri",fontSize:17,bold:true,color:INK,margin:0});
 s.addText(l[2],{x:x+0.1,y:3.74,w:2.58,h:0.4,align:"center",fontFace:"Calibri",fontSize:12,bold:true,color:l[4],margin:0});
 s.addText(l[3],{x:x+0.12,y:4.12,w:2.54,h:0.55,align:"center",fontFace:"Calibri",fontSize:11.5,color:MUTE,margin:0,lineSpacingMultiple:1.05});
 if(i<layers.length-1) s.addImage({data:ICONS["FaArrowRight"],x:x+2.79,y:3.05,w:0.22,h:0.22});
});
s.addText("Wie ein Warenwirtschaftssystem im Kleinen – nur dass ich jede Schicht selbst entworfen habe.",
 {x:0.72,y:5.0,w:11.9,h:0.4,align:"center",fontFace:"Calibri",fontSize:13,italic:true,color:TEAL2,margin:0});
parallel(s,"SAP/Amondis als fertige Großsysteme","eigene Schichten: View · Model · DAO · DB");

// ===== 10 DATENMODELL =====
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

// ===== 11 KERNFUNKTIONEN =====
s=pres.addSlide(); s.background={path:"assets/bg_light.png"};
title(s,"Die Kernfunktionen","Was die Anwendung kann – jede Funktion mit Praxisbezug");
const funcs=[["FaListUl","CRUD über alle Daten","Stammdaten anlegen, ändern, löschen",TEAL],
 ["FaSearch","Suche & Filter","Material nach Name oder Station finden",TEAL2],
 ["FaBell","Mindestbestand-Warnung","kritische Bestände rot hervorgehoben",OLD],
 ["FaExchangeAlt","Umlagerung","Material zwischen Lagern umbuchen (Bonus)",MINT],
 ["FaClipboardList","Bestellverwaltung","offene Bestellungen mit Status",TEAL],
 ["FaCalculator","Bestandsberechnung","Bestand live aus den Bewegungen",TEAL2]];
funcs.forEach((f,i)=>{
 const x=0.72+(i%3)*4.0, y=1.85+Math.floor(i/3)*2.2;
 s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x,y,w:3.75,h:2.0,fill:{color:CARD},line:{color:LINE,width:1},rectRadius:0.1,shadow:mkShadow()});
 iconCircle(s,x+0.28,y+0.3,0.72,f[0],f[3]);
 s.addText(f[1],{x:x+1.15,y:y+0.35,w:2.5,h:0.7,fontFace:"Calibri",fontSize:13.5,bold:true,color:INK,margin:0,valign:"middle"});
 s.addText(f[2],{x:x+0.25,y:y+1.2,w:3.3,h:0.7,fontFace:"Calibri",fontSize:12,color:MUTE,margin:0,lineSpacingMultiple:1.05});
});
parallel(s,"Kommissionierung · ABC-Auswertung · Inventur","Suche · Warnung · Umlagerung · Historie");

// ===== 12 DESIGN-HIGHLIGHT =====
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

// ===== 13 HERAUSFORDERUNGEN =====
s=pres.addSlide(); s.background={path:"assets/bg_light.png"};
title(s,"Herausforderungen & Lösungen","Aus dem Projekttagebuch – dieselbe Systematik wie bei der Prozessanpassung");
const ch=[["FaBug","Falscher Spaltenname","PK hieß im Code anders als in der DB → SQLException","an allen Stellen vereinheitlicht"],
 ["FaPlug","DB-Verbindung scheiterte","falscher Datenbankname & Port in der JDBC-URL","auf krankenhaus_lager / 3324 korrigiert"],
 ["FaGraduationCap","JavaFX selbst erarbeitet","TableView, FilteredList, FXML waren nicht im Unterricht","über Eigenrecherche + Lernhandbuch gelöst"]];
ch.forEach((c,i)=>{
 const y=1.9+i*1.45;
 s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x:0.72,y,w:11.9,h:1.28,fill:{color:CARD},line:{color:LINE,width:1},rectRadius:0.1,shadow:mkShadow()});
 iconCircle(s,1.0,y+0.29,0.7,c[0],i<2?OLD:TEAL2);
 s.addText(c[1],{x:1.95,y:y+0.18,w:4.2,h:0.5,fontFace:"Calibri",fontSize:14.5,bold:true,color:INK,margin:0,valign:"middle"});
 s.addText(c[2],{x:6.0,y:y+0.2,w:3.6,h:0.9,fontFace:"Calibri",fontSize:11.5,color:MUTE,margin:0,valign:"middle",lineSpacingMultiple:1.05});
 s.addImage({data:ICONS["FaArrowRight"],x:9.62,y:y+0.53,w:0.22,h:0.22});
 s.addText(c[3],{x:9.95,y:y+0.2,w:2.55,h:0.9,fontFace:"Calibri",fontSize:11.5,bold:true,color:TEAL2,margin:0,valign:"middle",lineSpacingMultiple:1.05});
});
parallel(s,"Amondis-Oberflächen im Betrieb anpassen","Code analysieren, Fehler isolieren, lösen");

// ===== 14 ERGEBNIS =====
s=pres.addSlide(); s.background={path:"assets/bg_light.png"};
title(s,"Das Ergebnis","Alle Pflichtanforderungen erfüllt – plus eine Zusatzfunktion");
stat(s,0.72,1.85,2.85,"6","Tabellen (Soll: ≥5)",TEAL2);
stat(s,3.79,1.85,2.85,"~1.700","Zeilen Java-Code",TEAL);
stat(s,6.86,1.85,2.85,"15+","Klassen (MVC/DAO)",MINT);
stat(s,9.93,1.85,2.7,"+1","Bonus: Umlagerung",OLD);
const done=["MySQL-Datenbank mit 6 Tabellen (3NF)","Vollständiges CRUD über alle Entitäten",
 "MVC-Architektur mit DAO-Schicht","Such- und Filterfunktion",
 "Mindestbestand-Warnung in Rot","Umlagerung als zusätzliches Feature"];
done.forEach((t,i)=>{
 const x=0.72+(i%2)*6.0, y=3.7+Math.floor(i/2)*0.7;
 s.addImage({data:ICONS["FaCheckCircle"],x:x,y:y+0.03,w:0.3,h:0.3});
 s.addText(t,{x:x+0.42,y:y-0.03,w:5.5,h:0.45,fontFace:"Calibri",fontSize:14,color:INK,valign:"middle",margin:0});
});
parallel(s,"reales Ziel: digitaler Lagerprozess","Projektziel erreicht – lauffähige Software");

// ===== 15 FAZIT =====
s=pres.addSlide(); s.background={path:"assets/bg_dark.png"};
chip(s,0.7,0.7,{t:"FAZIT",w:1.3},MINT,"0B3C49");
s.addText("Vom Fax-Zettel zum eigenen System",{x:0.7,y:1.7,w:12,h:0.9,fontFace:"Georgia",fontSize:34,bold:true,color:"FFFFFF",margin:0});
s.addText([
 {text:"Ich habe die Digitalisierung eines Krankenhauslagers als ",options:{color:"D7E7E9"}},
 {text:"Anwender",options:{color:OLDSOFT,bold:true}},
 {text:" begleitet – und sie hier als ",options:{color:"D7E7E9"}},
 {text:"Entwickler",options:{color:MINT,bold:true}},
 {text:" von Grund auf neu gedacht.",options:{color:"D7E7E9"}}],
 {x:0.7,y:2.75,w:11.6,h:0.9,fontFace:"Calibri",fontSize:18,margin:0,lineSpacingMultiple:1.15});
const tk=[["FaWarehouse","Praxis verstanden","Ich kenne das Problem aus dem echten Lageralltag.",OLD],
 ["FaLaptopCode","Selbst umgesetzt","Datenmodell, Logik und Oberfläche eigenständig gebaut.",MINT],
 ["FaCheckCircle","Ziel erreicht","Alle SRH-Anforderungen erfüllt, plus Zusatzfeature.",TEAL2]];
tk.forEach((t,i)=>{
 const x=0.9+i*3.95;
 s.addShape(pres.shapes.ROUNDED_RECTANGLE,{x,y:3.95,w:3.6,h:1.95,fill:{color:"103F4C"},line:{color:t[3],width:1.2},rectRadius:0.12,shadow:mkShadow()});
 iconCircle(s,x+0.28,4.2,0.62,t[0],t[3]);
 s.addText(t[1],{x:x+1.05,y:4.25,w:2.4,h:0.6,fontFace:"Calibri",fontSize:14,bold:true,color:"FFFFFF",margin:0,valign:"middle"});
 s.addText(t[2],{x:x+0.28,y:4.95,w:3.05,h:0.8,fontFace:"Calibri",fontSize:11.5,color:"BFD7DA",margin:0,lineSpacingMultiple:1.05});
});
s.addText("Vielen Dank – ich freue mich auf Ihre Fragen.",{x:0.7,y:6.25,w:12,h:0.5,align:"center",fontFace:"Georgia",fontSize:18,italic:true,color:MINT,margin:0});

// ---------- Speaker notes ----------
const notes=[
"Begrüßung. Titel nennen. Aufhänger: Diese Präsentation hat zwei Ebenen – mein realer Berufshintergrund im Krankenhauslager und das Softwareprojekt. Beide laufen parallel.",
"Glaubwürdigkeit: 3 Jahre Teamleiter im Zentrallager der Diakonissen Speyer. Zahlen: rund 1.200 lagergeführte Artikel, über 24.000 Artikel, die über den Einkauf bestellt werden, 15.000 Lieferscheine/Jahr, 12 Mitarbeiter geführt. Warenwirtschaft war mySAP ERP, Modul MM.",
"Ausgangslage damals: Nur das Zentrallager hatte SAP. Stationen und die 13 Außenhäuser füllten Anforderungszettel aus, per Fax/Mail. Im Zentrallager manuell in SAP eingetippt, Kommissionierliste erstellt, bearbeitet, ausgefahren. Schwachstellen benennen.",
"Die Wende: Pilotprojekt Amondis-Logistik – federführend bei Anpassung der Prozesse, Zuschnitt der Oberflächen, Einführung im laufenden Betrieb. Jeder Standort bestellt direkt digital, Mehrfachbestellungen zusammenfassbar. WICHTIG: Das System trennt jetzt automatisch ~1.200 lagergeführte Artikel von über 24.000 Artikeln, die direkt an den Einkauf gehen – diese automatische Aufteilung war vorher nicht möglich.",
"Kernbotschaft / Brückenschlag: Damals war ich Anwender und habe digitalisiert. Heute stehe ich auf der anderen Seite – als Entwickler. Das ist meine Motivation für genau dieses Projekt.",
"Das Projekt: Lagerverwaltung für ein fiktives Krankenhaus. Betonen, dass es synonym zur realen Situation ist – gleiches Problem, andere Seite des Tisches.",
"SRH-Pflichtanforderungen: MySQL mit mind. 5 Tabellen, CRUD, MVC, Suche/Filter. Jede mit der realen Lager-Aufgabe verknüpfen.",
"Ist-Analyse des fiktiven Krankenhauses = Spiegel der Insel-Situation: manuelle Listen, kein Warnsystem, keine Historie. Soll-Konzept = das, was Amondis im echten Lager geschaffen hat.",
"Architektur: MVC + DAO, Java 17, JavaFX, MySQL 8, lokal. View → Model → DAO → DB. Warenwirtschaftssystem im Kleinen, jede Schicht selbst entworfen.",
"ER-Diagramm: 6 Tabellen in 3. Normalform. Beziehungen / Fremdschlüssel kurz erklären. Parallele: früher Stammdaten in SAP gepflegt, jetzt selbst modelliert.",
"Kernfunktionen (ggf. Live-Demo): CRUD, Suche/Filter, rote Mindestbestand-Warnung, Umlagerung (Bonus), Bestellverwaltung, berechneter Bestand.",
"Designentscheidung: Bestand wird nicht gespeichert, sondern aus den Bewegungen berechnet (SUM EINGANG − AUSGANG). Vorteile: keine Widersprüche, kostenlose Historie. Im Fachgespräch begründbar.",
"Herausforderungen ehrlich: zwei behobene Bugs (Spaltenname, DB-Verbindung), JavaFX im Selbststudium. Gleiche Systematik wie bei der Amondis-Prozessanpassung.",
"Ergebnis: alle Pflichtkriterien erfüllt, 6 Tabellen, ~1.700 Zeilen Code, plus Umlagerung als Zusatzfeature. Soll-Ist passt.",
"Fazit, Bogen schließen: vom Fax-Zettel zum eigenen System. Ich stand auf beiden Seiten. Dank und Überleitung zu Fragen."
];
pres.slides.forEach((sl,i)=>{ if(notes[i]) sl.addNotes(notes[i]); });

await pres.writeFile({fileName:"Praesentation_Lagerverwaltung_Sascha_Schulz.pptx"});
console.log("PPTX geschrieben:", pres.slides.length, "Folien");
})();
