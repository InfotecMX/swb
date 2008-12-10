/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
      var context="/swb";

      dojo.require("dijit.Menu");
      dojo.require("dijit._Calendar");
      dojo.require("dijit.ColorPalette");
      dojo.require("dijit.ProgressBar");
      dojo.require("dijit.TitlePane");
      dojo.require("dijit.Tooltip");
      dojo.require("dijit.Tree");

      dojo.require("dojox.form.TimeSpinner");
      dojo.require("dojox.layout.ContentPane");

      // editor:
      dojo.require("dijit.Editor");

      // dnd:
      dojo.require("dojo.dnd.Source");

      // various Form elemetns

      dojo.require("dijit.form.CheckBox");
      dojo.require("dijit.form.Textarea");
      dojo.require("dijit.form.FilteringSelect");
      dojo.require("dijit.form.TextBox");
      dojo.require("dijit.form.DateTextBox");
      dojo.require("dijit.form.TimeTextBox");
      dojo.require("dijit.form.Button");
      dojo.require("dijit.InlineEditBox");
      dojo.require("dijit.form.NumberSpinner");
      dojo.require("dijit.form.Slider");

      // layouts used in page

      dojo.require("dijit.layout.AccordionContainer");
      dojo.require("dijit.layout.ContentPane");
      dojo.require("dijit.layout.TabContainer");
      dojo.require("dijit.layout.BorderContainer");
      dojo.require("dijit.Dialog");

      // scan page for widgets and instantiate them
      dojo.require("dojo.parser");

      // humm?
      dojo.require("dojo.date.locale");

      // for the Tree
      dojo.require("dojo.data.ItemFileWriteStore");
      dojo.require("dojo.data.ItemFileReadStore");

      dojo.addOnLoad(function() {

          var start = new Date().getTime();
          //dojo.parser.parse(dojo.byId('container'));
          console.info("Total parse time: " + (new Date().getTime() - start) + "ms");

          //dojo.byId('loaderInner').innerHTML += " done.";
          setTimeout(function hideLoader(){
              var loader = dojo.byId('loader');
              dojo.fadeOut({ node: loader, duration:500,
                  onEnd: function(){
                      loader.style.display = "none";
                  }
              }).play();
          }, 250);
      });

      function getHtml(url, tagid)
      {
          dojo.xhrGet({
              url: url,
              load: function(response, ioArgs){
                if(dojo.byId(tagid)) {
                  dojo.byId(tagid).innerHTML = response;
                }else {
                  alert("No existe ningún elemento con id " + tagid);
                }
                return response;
              },
              error: function(response, ioArgs){
                  if(dojo.byId(tagid)) {
                    dojo.byId(tagid).innerHTML = "<p>Ocurrió un error con respuesta:<br />" + response + "</p>";
                  }else {
                    alert("No existe ningún elemento con id " + tagid);
                  }
                  return response;
              },
              handleAs: "text"
          });
      }

      function getJSON(url)
      {
          //alert("load:"+url);
          var ret=[];
          var obj=dojo.xhrGet({
              url: url,
              sync: true,
              load: function(data){
                  //obj=data;
                  //alert("1:"+data);
                  ret=data;
                  //return data;
              },
              error: function(data){
                  alert("An error occurred, with response: " + data);
                  //return data;
              },
              handleAs: "json"
          });
          //alert(url+" "+ret);
          return ret;
      }


      function showDialog(url)
      {
          dojo.xhrGet({
              url: url,
              load: function(response, ioArgs){
                  dijit.byId('swbDialogImp').setContent(response);
                  dijit.byId('swbDialog').show();
                  return response;
              },
              error: function(response, ioArgs){
                  dijit.byId('swbDialogImp').setContent("Error: "+response);
                  dijit.byId('swbDialog').show();
                  return response;
              },
              handleAs: "text"
          });
      }

      function getContentPanel(reference)
      {
          if(!reference)return null;
          //alert("reference:"+reference.getAttribute("dojoType"));
          var att=reference.getAttribute("dojoType");
          if(att && (att=="dijit.layout.ContentPane" || att=="dojox.layout.ContentPane"))
          {
                  return dijit.byNode(reference);
          }else
          {
                  return getContentPanel(reference.parentNode);
          }
      }

      function submitUrl(url, reference)
      {
          var panel=getContentPanel(reference);
          //alert("panel:"+panel);
          dojo.xhrGet({
              url: url,
              load: function(response, ioArgs){
                  if(panel!=null)panel.setContent(response);
                  return response;
              },
              error: function(response, ioArgs){
                  if(panel!=null)panel.setContent("An error occurred, with response: " + response);
                  return response;
              },
              handleAs: "text"
          });
      }

      function submitForm(formid)
      {
          var obj=dojo.byId(formid);
          var objd=dijit.byId(formid);
          var fid=formid;
          //alert("id:"+formid+" "+"dojo:"+obj +" dijit:"+objd);
          if(!obj && objd) //si la forma esta dentro de un dialog
          {
              obj=objd.domNode;
              fid=obj;
          }

          if(!objd || objd.isValid())
          {
              //alert("entra2");
              dojo.xhrPost({
                  // The page that parses the POST request
                  contentType: "application/x-www-form-urlencoded; charset=utf-8",

                  //handleAs: "text",

                  url: obj.action,

                  // Name of the Form we want to submit
                  form: fid,

                  // Loads this function if everything went ok
                  load: function (data) {
                          var panel=getContentPanel(obj);
                          //alert("div:"+panel.id);
                          if(panel)
                          {
                              try
                              {
                              panel.setContent(data);
                              //alert("data:"+data);
                              }catch(e){alert(e.message);}
                          }
                          //dijit.byId('swbDialog').hide();
                          //div_node.innerHTML = data;
                          //dojo.parser.parse(div_node,true);
                  },
                  // Call this function if an error happened
                  error: function (error) {
                          console.error ('Error: ', error);
                  }
              });
          }else
          {
              alert("Datos Invalidos...");
          }
      }

      function addNewTab(id, title, url)
      {
          var objid=id+"/tab";
          var newTab = dijit.byId(objid);
          if(!url)url=context+"/swbadmin/jsp/objectTab.jsp";
          if(newTab==null)
          {
              newTab = new dojox.layout.ContentPane(
              {
                  id: objid,
                  closeable:'true',
                  onClose: function()
                  {
                      var ret=true;
                      //ret=confirm("Do you really want to Close this?");
                      if(ret)
                      {
                          var d=dijit.byId(objid+"2");
                          if(d)
                          {
                              var arr=d.getChildren();
                              for (var n = 0; n < arr.length; n++)
                              {
                                  arr[n].setContent(null);
                              }
                          }
                      }
                      return ret;
                  },
                  title: title,
                  href: url+"?suri="+encodeURIComponent(id)
              });
              newTab.closable=true;
              tabs.addChild(newTab);
              tabs.selectChild(newTab);
          }else
          {
              tabs.selectChild(newTab);
          }
      }

      var act_item;
      var act_store;

      function executeAction(store, item, action)
      {
          act_item=item;
          act_store=store;
          if(action.length)action=action[0];
          //alert("action:"+action+" ["+action.name+"] "+action.length);
          if(action.name=="reload")
          {
              reloadTreeNode(store,item);
          }else if(action.name=="newTab")
          {
              addNewTab(item.id, item.title, action.value);
          }else if(action.name=="showDialog")
          {
                showDialog(action.value);
          }else if(action.name=="showStatusURL")
          {
                showStatusURL(action.value);
          }
      }

      function executeTreeNodeEvent(store, item, eventname)
      {
            var event=getTreeNodeEvent(store,item,eventname);
            if(event)
            {
                //alert("event:"+event.name+" "+event.action);
                executeAction(store,item,event.action);
            }
      }

      //regrasa evento de nombre eventname asociado al nodo item del store
      function getTreeNodeEvent(store, item, eventname)
      {
            var events=store.getValues(item, "events");
            //var events=item.events;
            if(events)
            {
                for(var x=0;x<events.length;x++)
                {
                    if(events[x].name==eventname)
                    {
                        return events[x];
                    }
                }
            }
            return null;
      }

      function removeChilds(store,item)
      {
          var items=item.children;
          if(items)
          {
              for (var i=0; i<items.length;i++)
              {
                  removeChilds(store,items[i]);
                  store.deleteItem(items[i]);
              }
              store.save();
          }
      }

      function updateTreeNode(store, item, jsonNode)
      {
          if(!store)store=act_store;
          if(!item)item=act_item;
          var onlyNode=false;
          if(!jsonNode)
          {
              onlyNode=true;
              jsonNode=getJSON(context+"/swbadmin/jsp/Tree.jsp?suri="+encodeURIComponent(item.id))[0];
          }

          store.setValues(item, "title", jsonNode.title);
          store.setValues(item, "type", jsonNode.type);
          store.setValues(item, "icon", jsonNode.icon);
          if(!onlyNode)
          {
            if(jsonNode.events)store.setValues(item, "events", jsonNode.events);
            else store.unsetAttribute(item, "events");
          }
          if(jsonNode.menus)store.setValues(item, "menus", jsonNode.menus);
          else store.unsetAttribute(item, "menus");
          store.save();
      }

      function setWaitCursor()
      {
          document.body.style.cursor="wait";
          dojo.byId("leftAccordion").style.cursor="wait";
      }

      function setDefaultCursor()
      {
          document.body.style.cursor="default";
          dojo.byId("leftAccordion").style.cursor="default";
      }

      function removeTreeNode(store, item)
      {
          if(!store)store=act_store;
          if(!item)item=act_item;
          var objid=item.id+"/tab";
          setWaitCursor();
          //alert("reload:"+item.id);
          removeChilds(store,item);
          store.deleteItem(item);
          store.save();

          var newTab = dijit.byId(objid);
          if(newTab)
          {
              tabs.closeChild(newTab);
          }

          setDefaultCursor();
      }

      function reloadTreeNode(store, item)
      {
          if(!store)store=act_store;
          if(!item)item=act_item;
          setWaitCursor();
          //alert("reload:"+item.id);
          removeChilds(store,item);
          var arr=getJSON(context+"/swbadmin/jsp/Tree.jsp?suri="+encodeURIComponent(item.id))
          updateTreeNode(store,item,arr[0]);
          //alert("arr:"+arr[0].id);
          var items=arr[0].children;
          //alert("nitem:"+items.length);
          for (var i=0; i<items.length;i++)
          {
                //alert("item:"+i+" "+items[i].id);
                var pInfo={parent:item, attribute:"children"};
                var ite=store.newItem(items[i],pInfo);
                if(items[i].hasChilds)
                {
                    //alert("hasChilds:"+items[i].id+" "+ite);
                    pInfo={parent:ite, attribute:"children"};
                    var dummy={"id":items[i].id+"_tmp_","icon":"swbIconWebSite","title":"dummy"};
                    store.newItem(dummy,pInfo);
                }
          }
          store.save();
          setDefaultCursor();
      }

      function actionDone(){
          alert("Acción realizada.");
      }

      function actionFailed(){
          alert("Error al ejecutar la acción.");
      }

      function loadScript(id, filepath)
      {
         if (document.getElementById(id))
         { // Already exists
             return;
         }
         var head = document.getElementsByTagName("head")[0];
         if(head)
         {
             script = document.createElement('script');
             script.id = id;
             script.type = 'text/javascript';
             script.src = filepath;
             head.appendChild(script);
         }
     }

     var ini=-30;
     var sy=ini;
     var si=5;
     function scroll()
     {
        var t=30;
        var ele=dojo.byId('status');
        if(sy>0)
        {
            si=-2;
            t=5000;
        }
        sy+=si;
        ele.style.bottom=sy+'px';
        if(sy>ini)setTimeout(scroll,t);
     }

     function showStatus(msg)
     {
         var ele=dijit.byId('status');
         ele.setContent(msg);
         //ele.innerHTML=msg;
         sy=ini;
         si=2;
         scroll();
     }

      function showStatusURL(url)
      {
          dojo.xhrGet({
              url: url,
              load: function(response, ioArgs){
                showStatus(response);
              },
              error: function(response, ioArgs){
                showStatus("Error: "+response);
              },
              handleAs: "text"
          });
      }

      function getItem(store,id)
      {
          var ret=null;
          var x=store.fetchItemByIdentity(
          {
              identity: id,
              onItem: function(item)
              {
                  ret=item;
              }
          });
          return ret;
      }