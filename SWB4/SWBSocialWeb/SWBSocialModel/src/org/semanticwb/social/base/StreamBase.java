package org.semanticwb.social.base;


   /**
   * Clase que contendra los streams que configurados para cada usuario 
   */
public abstract class StreamBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,org.semanticwb.model.Filterable,org.semanticwb.social.Geolocable,org.semanticwb.social.SocialRuleRefable,org.semanticwb.model.Trashable,org.semanticwb.model.FilterableClass,org.semanticwb.model.FilterableNode,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Referensable
{
   /**
   * Número de Iteraciones que se ha tenido el Stream. Este dato se utiliza en conjunto con el de la propiedad "promPostNumber" para poder determinar si el número de mensajes que accesaron al Stream amerita que se envíe una notificación (alerta) indicando que llegaron mas mensajes al Stream que los que se tiene en promedio en un tiempo determinado.
   */
    public static final org.semanticwb.platform.SemanticProperty social_streamIterations=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#streamIterations");
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * Redes sociales asociadas al stream. En estas redes sociales se escuchara la frase asociada a un stream.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasStream_socialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasStream_socialNetwork");
   /**
   * Clase a Cambiar despues por "Relacional".Clase en la cual se almacenan los usuarios que escriben los PostIn que llegan. No se puso como identificador de las instancias de esta clase el id que maneja el usuario en la red social, ya que un identificador de una red social, puede ser el mismo para otra red social, pero obviamnete para otro usuario.Es por ello que se puso como AutoGenID esta clase y por ello se maneja por separado el id de un usuario en una determinada red social, esto en la propiedad snu_id.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetworkUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetworkUser");
   /**
   * Lista todos las instancias de SocialNetworkUser que esten asociados con un determinado Stream, se utiliza para las campañas.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasSocialNetUserInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasSocialNetUserInv");
   /**
   * Clase en la que se guardan datos que sirven para realizar una siguiente busqueda en una determinada red social y en un determinado stream.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetStreamSearch=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetStreamSearch");
   /**
   * El stream puede tener varias instancias de la clase SocialNetStreamSearch, una por cada red social que tenga asignada.Si se elimina un Stream, se eliminan los objetos de esta clase (SocialNetStreamSerch) Asociados.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasSocialNetStreamSearch=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasSocialNetStreamSearch");
   /**
   * Logotipo del Stream
   */
    public static final org.semanticwb.platform.SemanticProperty social_stream_logo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#stream_logo");
   /**
   * Descarta estas palabras de la búsqueda
   */
    public static final org.semanticwb.platform.SemanticProperty social_stream_notPhrase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#stream_notPhrase");
   /**
   * Propiedad que indica si en el stream se desea aceptar que entren los mensajes que sean clasificados con sentimiento negativo
   */
    public static final org.semanticwb.platform.SemanticProperty social_filterSentimentalNegative=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#filterSentimentalNegative");
   /**
   * Lapso de tiempo en que se busca la información. Ej. Cada x tiempo
   */
    public static final org.semanticwb.platform.SemanticProperty social_stream_PoolTime=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#stream_PoolTime");
   /**
   * Maxima cantidad de registros (mensajes) que almacenara un stream
   */
    public static final org.semanticwb.platform.SemanticProperty social_stream_maxMsg=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#stream_maxMsg");
   /**
   * Propiedad que indica si el stream va manejar conexiones abiertas en las cuentas de redes sociales que así lo permitan, en este momento solo twitter con su "Stream Api" lo maneja.
   */
    public static final org.semanticwb.platform.SemanticProperty social_keepAliveManager=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#keepAliveManager");
   /**
   * Frase a monitorear en un determinado stream, cada stream tiene sus propias frasea a monitorear.
   */
    public static final org.semanticwb.platform.SemanticProperty social_stream_phrase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#stream_phrase");
   /**
   * Número mximo de días que un mensaje va a permanecer en el stream antes de ser eliminado del mismo.
   */
    public static final org.semanticwb.platform.SemanticProperty social_stream_maxDays=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#stream_maxDays");
   /**
   * Propiedad para escribir correos electronicos de los usuarios que recibiran alertas. Si entran mas mensajes (listener) a un Stream del promedio.
   */
    public static final org.semanticwb.platform.SemanticProperty social_streamEmail2Alerts=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#streamEmail2Alerts");
   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");
   /**
   * PostIn asociados a un Stream. Si se elimina el Stream, se eliminan estos PostIn, ya que si no se hiciera, no se podrían ver desde ningún lado, ya que no tuvieran un Stream asociado.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPostInStreamInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostInStreamInv");
   /**
   * Busca en estas cuentas
   */
    public static final org.semanticwb.platform.SemanticProperty social_stream_fromAccount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#stream_fromAccount");
   /**
   * Propiedad que indica si en el stream se desea aceptar que entren los mensajes que sean clasificados con sentimiento positivo
   */
    public static final org.semanticwb.platform.SemanticProperty social_filterSentimentalPositive=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#filterSentimentalPositive");
   /**
   * Valor númerico minimo de klout que se desea filtrar para un stream. Los usuarios que tengan este klout o más y que hablen en las redes sociales configuradas para el stream, seran tomados sus mensajes para ser guardados en el sistema.
   */
    public static final org.semanticwb.platform.SemanticProperty social_stream_KloutValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#stream_KloutValue");
   /**
   * Catalogo de temas de un modelo (Marca)
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialTopic=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialTopic");
   /**
   * Temas en los que se clasificaran los mensajes
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasTopics2Apply=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasTopics2Apply");
   /**
   * Porcentaje de aumento en el promedio de los PostIns de entrada en el Stream. Si se llega a este porcentaje o mas en una nueva iteración del Stream, se envía notificación (alerta).
   */
    public static final org.semanticwb.platform.SemanticProperty social_streamPercentageAlert=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#streamPercentageAlert");
   /**
   * Propiedad que indica si en el stream se desea aceptar que entren los mensajes que sean clasificados con intensidad baja
   */
    public static final org.semanticwb.platform.SemanticProperty social_filterIntensityLow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#filterIntensityLow");
   /**
   * Propiedad que indica si en el stream se desea aceptar que entren los mensajes que sean clasificados con intensidad alta
   */
    public static final org.semanticwb.platform.SemanticProperty social_filterIntensityHigh=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#filterIntensityHigh");
   /**
   * Propiedad que indica si en el stream se desea aceptar que entren los mensajes que sean clasificados con sentimiento neutro
   */
    public static final org.semanticwb.platform.SemanticProperty social_filterSentimentalNeutral=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#filterSentimentalNeutral");
   /**
   * This property will accept an exact phrase to be search over SocialNetworks
   */
    public static final org.semanticwb.platform.SemanticProperty social_stream_exactPhrase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#stream_exactPhrase");
   /**
   * Propiedad que indica si en el stream se desea aceptar que entren los mensajes que sean clasificados con intensidad media
   */
    public static final org.semanticwb.platform.SemanticProperty social_filterIntensityMedium=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#filterIntensityMedium");
   /**
   * Número de post en el periodo de tiempo determinado en la propiedad stream_PoolTime
   */
    public static final org.semanticwb.platform.SemanticProperty social_promPostNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#promPostNumber");
   /**
   * Busca todas las palabras definidas en este campo
   */
    public static final org.semanticwb.platform.SemanticProperty social_stream_allPhrases=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#stream_allPhrases");
   /**
   * Clase que contendra los streams que configurados para cada usuario
   */
    public static final org.semanticwb.platform.SemanticClass social_Stream=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Stream");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Stream");

    public static class ClassMgr
    {
       /**
       * Returns a list of Stream for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreams(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.Stream for all models
       * @return Iterator of org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreams()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream>(it, true);
        }

        public static org.semanticwb.social.Stream createStream(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.Stream.ClassMgr.createStream(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.Stream
       * @param id Identifier for org.semanticwb.social.Stream
       * @param model Model of the org.semanticwb.social.Stream
       * @return A org.semanticwb.social.Stream
       */
        public static org.semanticwb.social.Stream getStream(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Stream)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.Stream
       * @param id Identifier for org.semanticwb.social.Stream
       * @param model Model of the org.semanticwb.social.Stream
       * @return A org.semanticwb.social.Stream
       */
        public static org.semanticwb.social.Stream createStream(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Stream)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.Stream
       * @param id Identifier for org.semanticwb.social.Stream
       * @param model Model of the org.semanticwb.social.Stream
       */
        public static void removeStream(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.Stream
       * @param id Identifier for org.semanticwb.social.Stream
       * @param model Model of the org.semanticwb.social.Stream
       * @return true if the org.semanticwb.social.Stream exists, false otherwise
       */

        public static boolean hasStream(String id, org.semanticwb.model.SWBModel model)
        {
            return (getStream(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined SocialNetwork
       * @param value SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamBySocialNetwork(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasStream_socialNetwork, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined SocialNetwork
       * @param value SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamBySocialNetwork(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasStream_socialNetwork,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined SocialNetUserInv
       * @param value SocialNetUserInv of the type org.semanticwb.social.SocialNetworkUser
       * @param model Model of the org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamBySocialNetUserInv(org.semanticwb.social.SocialNetworkUser value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetUserInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined SocialNetUserInv
       * @param value SocialNetUserInv of the type org.semanticwb.social.SocialNetworkUser
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamBySocialNetUserInv(org.semanticwb.social.SocialNetworkUser value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetUserInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined SocialNetStreamSearch
       * @param value SocialNetStreamSearch of the type org.semanticwb.social.SocialNetStreamSearch
       * @param model Model of the org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamBySocialNetStreamSearch(org.semanticwb.social.SocialNetStreamSearch value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetStreamSearch, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined SocialNetStreamSearch
       * @param value SocialNetStreamSearch of the type org.semanticwb.social.SocialNetStreamSearch
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamBySocialNetStreamSearch(org.semanticwb.social.SocialNetStreamSearch value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetStreamSearch,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined PostInStreamInv
       * @param value PostInStreamInv of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamByPostInStreamInv(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInStreamInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined PostInStreamInv
       * @param value PostInStreamInv of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamByPostInStreamInv(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInStreamInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined Topics2Apply
       * @param value Topics2Apply of the type org.semanticwb.social.SocialTopic
       * @param model Model of the org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamByTopics2Apply(org.semanticwb.social.SocialTopic value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasTopics2Apply, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined Topics2Apply
       * @param value Topics2Apply of the type org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamByTopics2Apply(org.semanticwb.social.SocialTopic value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasTopics2Apply,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined SocialRuleRef
       * @param value SocialRuleRef of the type org.semanticwb.social.SocialRuleRef
       * @param model Model of the org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamBySocialRuleRef(org.semanticwb.social.SocialRuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined SocialRuleRef
       * @param value SocialRuleRef of the type org.semanticwb.social.SocialRuleRef
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamBySocialRuleRef(org.semanticwb.social.SocialRuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static StreamBase.ClassMgr getStreamClassMgr()
    {
        return new StreamBase.ClassMgr();
    }

   /**
   * Constructs a StreamBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Stream
   */
    public StreamBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the StreamIterations property
* @return int with the StreamIterations
*/
    public int getStreamIterations()
    {
        return getSemanticObject().getIntProperty(social_streamIterations);
    }

/**
* Sets the StreamIterations property
* @param value long with the StreamIterations
*/
    public void setStreamIterations(int value)
    {
        getSemanticObject().setIntProperty(social_streamIterations, value);
    }

/**
* Gets the GeoRadio property
* @return float with the GeoRadio
*/
    public float getGeoRadio()
    {
        return getSemanticObject().getFloatProperty(social_geoRadio);
    }

/**
* Sets the GeoRadio property
* @param value long with the GeoRadio
*/
    public void setGeoRadio(float value)
    {
        getSemanticObject().setFloatProperty(social_geoRadio, value);
    }
   /**
   * Gets all the org.semanticwb.social.SocialNetwork
   * @return A GenericIterator with all the org.semanticwb.social.SocialNetwork
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> listSocialNetworks()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork>(getSemanticObject().listObjectProperties(social_hasStream_socialNetwork));
    }

   /**
   * Gets true if has a SocialNetwork
   * @param value org.semanticwb.social.SocialNetwork to verify
   * @return true if the org.semanticwb.social.SocialNetwork exists, false otherwise
   */
    public boolean hasSocialNetwork(org.semanticwb.social.SocialNetwork value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasStream_socialNetwork,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a SocialNetwork
   * @param value org.semanticwb.social.SocialNetwork to add
   */

    public void addSocialNetwork(org.semanticwb.social.SocialNetwork value)
    {
        getSemanticObject().addObjectProperty(social_hasStream_socialNetwork, value.getSemanticObject());
    }
   /**
   * Removes all the SocialNetwork
   */

    public void removeAllSocialNetwork()
    {
        getSemanticObject().removeProperty(social_hasStream_socialNetwork);
    }
   /**
   * Removes a SocialNetwork
   * @param value org.semanticwb.social.SocialNetwork to remove
   */

    public void removeSocialNetwork(org.semanticwb.social.SocialNetwork value)
    {
        getSemanticObject().removeObjectProperty(social_hasStream_socialNetwork,value.getSemanticObject());
    }

   /**
   * Gets the SocialNetwork
   * @return a org.semanticwb.social.SocialNetwork
   */
    public org.semanticwb.social.SocialNetwork getSocialNetwork()
    {
         org.semanticwb.social.SocialNetwork ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasStream_socialNetwork);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetwork)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property ModifiedBy
   * @param value ModifiedBy to set
   */

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
    }
   /**
   * Remove the value for ModifiedBy property
   */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
   * Gets the ModifiedBy
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.social.SocialNetworkUser
   * @return A GenericIterator with all the org.semanticwb.social.SocialNetworkUser
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser> listSocialNetUserInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser>(getSemanticObject().listObjectProperties(social_hasSocialNetUserInv));
    }

   /**
   * Gets true if has a SocialNetUserInv
   * @param value org.semanticwb.social.SocialNetworkUser to verify
   * @return true if the org.semanticwb.social.SocialNetworkUser exists, false otherwise
   */
    public boolean hasSocialNetUserInv(org.semanticwb.social.SocialNetworkUser value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasSocialNetUserInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the SocialNetUserInv
   * @return a org.semanticwb.social.SocialNetworkUser
   */
    public org.semanticwb.social.SocialNetworkUser getSocialNetUserInv()
    {
         org.semanticwb.social.SocialNetworkUser ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasSocialNetUserInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetworkUser)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.social.SocialNetStreamSearch
   * @return A GenericIterator with all the org.semanticwb.social.SocialNetStreamSearch
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetStreamSearch> listSocialNetStreamSearches()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetStreamSearch>(getSemanticObject().listObjectProperties(social_hasSocialNetStreamSearch));
    }

   /**
   * Gets true if has a SocialNetStreamSearch
   * @param value org.semanticwb.social.SocialNetStreamSearch to verify
   * @return true if the org.semanticwb.social.SocialNetStreamSearch exists, false otherwise
   */
    public boolean hasSocialNetStreamSearch(org.semanticwb.social.SocialNetStreamSearch value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasSocialNetStreamSearch,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the SocialNetStreamSearch
   * @return a org.semanticwb.social.SocialNetStreamSearch
   */
    public org.semanticwb.social.SocialNetStreamSearch getSocialNetStreamSearch()
    {
         org.semanticwb.social.SocialNetStreamSearch ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasSocialNetStreamSearch);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetStreamSearch)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Created property
* @return java.util.Date with the Created
*/
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
* Sets the Created property
* @param value long with the Created
*/
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

/**
* Gets the Stream_logo property
* @return String with the Stream_logo
*/
    public String getStream_logo()
    {
        return getSemanticObject().getProperty(social_stream_logo);
    }

/**
* Sets the Stream_logo property
* @param value long with the Stream_logo
*/
    public void setStream_logo(String value)
    {
        getSemanticObject().setProperty(social_stream_logo, value);
    }

/**
* Gets the GeoCenterLongitude property
* @return float with the GeoCenterLongitude
*/
    public float getGeoCenterLongitude()
    {
        return getSemanticObject().getFloatProperty(social_geoCenterLongitude);
    }

/**
* Sets the GeoCenterLongitude property
* @param value long with the GeoCenterLongitude
*/
    public void setGeoCenterLongitude(float value)
    {
        getSemanticObject().setFloatProperty(social_geoCenterLongitude, value);
    }

/**
* Gets the Stream_notPhrase property
* @return String with the Stream_notPhrase
*/
    public String getStream_notPhrase()
    {
        return getSemanticObject().getProperty(social_stream_notPhrase);
    }

/**
* Sets the Stream_notPhrase property
* @param value long with the Stream_notPhrase
*/
    public void setStream_notPhrase(String value)
    {
        getSemanticObject().setProperty(social_stream_notPhrase, value);
    }

/**
* Gets the FilterSentimentalNegative property
* @return boolean with the FilterSentimentalNegative
*/
    public boolean isFilterSentimentalNegative()
    {
        return getSemanticObject().getBooleanProperty(social_filterSentimentalNegative);
    }

/**
* Sets the FilterSentimentalNegative property
* @param value long with the FilterSentimentalNegative
*/
    public void setFilterSentimentalNegative(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_filterSentimentalNegative, value);
    }

/**
* Gets the PoolTime property
* @return int with the PoolTime
*/
    public int getPoolTime()
    {
        return getSemanticObject().getIntProperty(social_stream_PoolTime);
    }

/**
* Sets the PoolTime property
* @param value long with the PoolTime
*/
    public void setPoolTime(int value)
    {
        getSemanticObject().setIntProperty(social_stream_PoolTime, value);
    }

/**
* Gets the Stream_maxMsg property
* @return int with the Stream_maxMsg
*/
    public int getStream_maxMsg()
    {
        return getSemanticObject().getIntProperty(social_stream_maxMsg);
    }

/**
* Sets the Stream_maxMsg property
* @param value long with the Stream_maxMsg
*/
    public void setStream_maxMsg(int value)
    {
        getSemanticObject().setIntProperty(social_stream_maxMsg, value);
    }

/**
* Gets the KeepAliveManager property
* @return boolean with the KeepAliveManager
*/
    public boolean isKeepAliveManager()
    {
        return getSemanticObject().getBooleanProperty(social_keepAliveManager);
    }

/**
* Sets the KeepAliveManager property
* @param value long with the KeepAliveManager
*/
    public void setKeepAliveManager(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_keepAliveManager, value);
    }

/**
* Gets the Phrase property
* @return String with the Phrase
*/
    public String getPhrase()
    {
        return getSemanticObject().getProperty(social_stream_phrase);
    }

/**
* Sets the Phrase property
* @param value long with the Phrase
*/
    public void setPhrase(String value)
    {
        getSemanticObject().setProperty(social_stream_phrase, value);
    }

/**
* Gets the Stream_maxDays property
* @return int with the Stream_maxDays
*/
    public int getStream_maxDays()
    {
        return getSemanticObject().getIntProperty(social_stream_maxDays);
    }

/**
* Sets the Stream_maxDays property
* @param value long with the Stream_maxDays
*/
    public void setStream_maxDays(int value)
    {
        getSemanticObject().setIntProperty(social_stream_maxDays, value);
    }

/**
* Gets the StreamEmail2Alerts property
* @return String with the StreamEmail2Alerts
*/
    public String getStreamEmail2Alerts()
    {
        return getSemanticObject().getProperty(social_streamEmail2Alerts);
    }

/**
* Sets the StreamEmail2Alerts property
* @param value long with the StreamEmail2Alerts
*/
    public void setStreamEmail2Alerts(String value)
    {
        getSemanticObject().setProperty(social_streamEmail2Alerts, value);
    }
   /**
   * Gets all the org.semanticwb.social.PostIn
   * @return A GenericIterator with all the org.semanticwb.social.PostIn
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> listPostInStreamInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn>(getSemanticObject().listObjectProperties(social_hasPostInStreamInv));
    }

   /**
   * Gets true if has a PostInStreamInv
   * @param value org.semanticwb.social.PostIn to verify
   * @return true if the org.semanticwb.social.PostIn exists, false otherwise
   */
    public boolean hasPostInStreamInv(org.semanticwb.social.PostIn value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostInStreamInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the PostInStreamInv
   * @return a org.semanticwb.social.PostIn
   */
    public org.semanticwb.social.PostIn getPostInStreamInv()
    {
         org.semanticwb.social.PostIn ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostInStreamInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostIn)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Stream_fromAccount property
* @return String with the Stream_fromAccount
*/
    public String getStream_fromAccount()
    {
        return getSemanticObject().getProperty(social_stream_fromAccount);
    }

/**
* Sets the Stream_fromAccount property
* @param value long with the Stream_fromAccount
*/
    public void setStream_fromAccount(String value)
    {
        getSemanticObject().setProperty(social_stream_fromAccount, value);
    }

/**
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

/**
* Gets the FilterSentimentalPositive property
* @return boolean with the FilterSentimentalPositive
*/
    public boolean isFilterSentimentalPositive()
    {
        return getSemanticObject().getBooleanProperty(social_filterSentimentalPositive);
    }

/**
* Sets the FilterSentimentalPositive property
* @param value long with the FilterSentimentalPositive
*/
    public void setFilterSentimentalPositive(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_filterSentimentalPositive, value);
    }

/**
* Gets the Stream_KloutValue property
* @return int with the Stream_KloutValue
*/
    public int getStream_KloutValue()
    {
        return getSemanticObject().getIntProperty(social_stream_KloutValue);
    }

/**
* Sets the Stream_KloutValue property
* @param value long with the Stream_KloutValue
*/
    public void setStream_KloutValue(int value)
    {
        getSemanticObject().setIntProperty(social_stream_KloutValue, value);
    }

/**
* Gets the GeoLanguage property
* @return String with the GeoLanguage
*/
    public String getGeoLanguage()
    {
        return getSemanticObject().getProperty(social_geoLanguage);
    }

/**
* Sets the GeoLanguage property
* @param value long with the GeoLanguage
*/
    public void setGeoLanguage(String value)
    {
        getSemanticObject().setProperty(social_geoLanguage, value);
    }
   /**
   * Gets all the org.semanticwb.social.SocialTopic
   * @return A GenericIterator with all the org.semanticwb.social.SocialTopic
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic> listTopics2Applies()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic>(getSemanticObject().listObjectProperties(social_hasTopics2Apply));
    }

   /**
   * Gets true if has a Topics2Apply
   * @param value org.semanticwb.social.SocialTopic to verify
   * @return true if the org.semanticwb.social.SocialTopic exists, false otherwise
   */
    public boolean hasTopics2Apply(org.semanticwb.social.SocialTopic value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasTopics2Apply,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Topics2Apply
   * @param value org.semanticwb.social.SocialTopic to add
   */

    public void addTopics2Apply(org.semanticwb.social.SocialTopic value)
    {
        getSemanticObject().addObjectProperty(social_hasTopics2Apply, value.getSemanticObject());
    }
   /**
   * Removes all the Topics2Apply
   */

    public void removeAllTopics2Apply()
    {
        getSemanticObject().removeProperty(social_hasTopics2Apply);
    }
   /**
   * Removes a Topics2Apply
   * @param value org.semanticwb.social.SocialTopic to remove
   */

    public void removeTopics2Apply(org.semanticwb.social.SocialTopic value)
    {
        getSemanticObject().removeObjectProperty(social_hasTopics2Apply,value.getSemanticObject());
    }

   /**
   * Gets the Topics2Apply
   * @return a org.semanticwb.social.SocialTopic
   */
    public org.semanticwb.social.SocialTopic getTopics2Apply()
    {
         org.semanticwb.social.SocialTopic ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasTopics2Apply);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialTopic)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the StreamPercentageAlert property
* @return int with the StreamPercentageAlert
*/
    public int getStreamPercentageAlert()
    {
        return getSemanticObject().getIntProperty(social_streamPercentageAlert);
    }

/**
* Sets the StreamPercentageAlert property
* @param value long with the StreamPercentageAlert
*/
    public void setStreamPercentageAlert(int value)
    {
        getSemanticObject().setIntProperty(social_streamPercentageAlert, value);
    }

/**
* Gets the Updated property
* @return java.util.Date with the Updated
*/
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
* Sets the Updated property
* @param value long with the Updated
*/
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

/**
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

/**
* Gets the FilterIntensityLow property
* @return boolean with the FilterIntensityLow
*/
    public boolean isFilterIntensityLow()
    {
        return getSemanticObject().getBooleanProperty(social_filterIntensityLow);
    }

/**
* Sets the FilterIntensityLow property
* @param value long with the FilterIntensityLow
*/
    public void setFilterIntensityLow(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_filterIntensityLow, value);
    }

/**
* Gets the Deleted property
* @return boolean with the Deleted
*/
    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(swb_deleted);
    }

/**
* Sets the Deleted property
* @param value long with the Deleted
*/
    public void setDeleted(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_deleted, value);
    }

/**
* Gets the Active property
* @return boolean with the Active
*/
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

/**
* Sets the Active property
* @param value long with the Active
*/
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }

/**
* Gets the GeoCenterLatitude property
* @return float with the GeoCenterLatitude
*/
    public float getGeoCenterLatitude()
    {
        return getSemanticObject().getFloatProperty(social_geoCenterLatitude);
    }

/**
* Sets the GeoCenterLatitude property
* @param value long with the GeoCenterLatitude
*/
    public void setGeoCenterLatitude(float value)
    {
        getSemanticObject().setFloatProperty(social_geoCenterLatitude, value);
    }

/**
* Gets the GeoDistanceUnit property
* @return String with the GeoDistanceUnit
*/
    public String getGeoDistanceUnit()
    {
        return getSemanticObject().getProperty(social_geoDistanceUnit);
    }

/**
* Sets the GeoDistanceUnit property
* @param value long with the GeoDistanceUnit
*/
    public void setGeoDistanceUnit(String value)
    {
        getSemanticObject().setProperty(social_geoDistanceUnit, value);
    }

/**
* Gets the FilterIntensityHigh property
* @return boolean with the FilterIntensityHigh
*/
    public boolean isFilterIntensityHigh()
    {
        return getSemanticObject().getBooleanProperty(social_filterIntensityHigh);
    }

/**
* Sets the FilterIntensityHigh property
* @param value long with the FilterIntensityHigh
*/
    public void setFilterIntensityHigh(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_filterIntensityHigh, value);
    }
   /**
   * Sets the value for the property Creator
   * @param value Creator to set
   */

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
    }
   /**
   * Remove the value for Creator property
   */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
   * Gets the Creator
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the FilterSentimentalNeutral property
* @return boolean with the FilterSentimentalNeutral
*/
    public boolean isFilterSentimentalNeutral()
    {
        return getSemanticObject().getBooleanProperty(social_filterSentimentalNeutral);
    }

/**
* Sets the FilterSentimentalNeutral property
* @param value long with the FilterSentimentalNeutral
*/
    public void setFilterSentimentalNeutral(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_filterSentimentalNeutral, value);
    }

/**
* Gets the Stream_exactPhrase property
* @return String with the Stream_exactPhrase
*/
    public String getStream_exactPhrase()
    {
        return getSemanticObject().getProperty(social_stream_exactPhrase);
    }

/**
* Sets the Stream_exactPhrase property
* @param value long with the Stream_exactPhrase
*/
    public void setStream_exactPhrase(String value)
    {
        getSemanticObject().setProperty(social_stream_exactPhrase, value);
    }

/**
* Gets the FilterIntensityMedium property
* @return boolean with the FilterIntensityMedium
*/
    public boolean isFilterIntensityMedium()
    {
        return getSemanticObject().getBooleanProperty(social_filterIntensityMedium);
    }

/**
* Sets the FilterIntensityMedium property
* @param value long with the FilterIntensityMedium
*/
    public void setFilterIntensityMedium(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_filterIntensityMedium, value);
    }
   /**
   * Gets all the org.semanticwb.social.SocialRuleRef
   * @return A GenericIterator with all the org.semanticwb.social.SocialRuleRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialRuleRef> listSocialRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialRuleRef>(getSemanticObject().listObjectProperties(social_hasSocialRuleRef));
    }

   /**
   * Gets true if has a SocialRuleRef
   * @param value org.semanticwb.social.SocialRuleRef to verify
   * @return true if the org.semanticwb.social.SocialRuleRef exists, false otherwise
   */
    public boolean hasSocialRuleRef(org.semanticwb.social.SocialRuleRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasSocialRuleRef,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a SocialRuleRef
   * @param value org.semanticwb.social.SocialRuleRef to add
   */

    public void addSocialRuleRef(org.semanticwb.social.SocialRuleRef value)
    {
        getSemanticObject().addObjectProperty(social_hasSocialRuleRef, value.getSemanticObject());
    }
   /**
   * Removes all the SocialRuleRef
   */

    public void removeAllSocialRuleRef()
    {
        getSemanticObject().removeProperty(social_hasSocialRuleRef);
    }
   /**
   * Removes a SocialRuleRef
   * @param value org.semanticwb.social.SocialRuleRef to remove
   */

    public void removeSocialRuleRef(org.semanticwb.social.SocialRuleRef value)
    {
        getSemanticObject().removeObjectProperty(social_hasSocialRuleRef,value.getSemanticObject());
    }

   /**
   * Gets the SocialRuleRef
   * @return a org.semanticwb.social.SocialRuleRef
   */
    public org.semanticwb.social.SocialRuleRef getSocialRuleRef()
    {
         org.semanticwb.social.SocialRuleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasSocialRuleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialRuleRef)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the PromPostNumber property
* @return float with the PromPostNumber
*/
    public float getPromPostNumber()
    {
        return getSemanticObject().getFloatProperty(social_promPostNumber);
    }

/**
* Sets the PromPostNumber property
* @param value long with the PromPostNumber
*/
    public void setPromPostNumber(float value)
    {
        getSemanticObject().setFloatProperty(social_promPostNumber, value);
    }

/**
* Gets the Stream_allPhrases property
* @return String with the Stream_allPhrases
*/
    public String getStream_allPhrases()
    {
        return getSemanticObject().getProperty(social_stream_allPhrases);
    }

/**
* Sets the Stream_allPhrases property
* @param value long with the Stream_allPhrases
*/
    public void setStream_allPhrases(String value)
    {
        getSemanticObject().setProperty(social_stream_allPhrases, value);
    }

   /**
   * Gets the SocialSite
   * @return a instance of org.semanticwb.social.SocialSite
   */
    public org.semanticwb.social.SocialSite getSocialSite()
    {
        return (org.semanticwb.social.SocialSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
