package org.semanticwb.model.base;

public interface CalendarableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swb_Calendar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Calendar");
    public static final org.semanticwb.platform.SemanticProperty swb_hasCalendar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasCalendar");
    public static final org.semanticwb.platform.SemanticClass swb_Calendarable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Calendarable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Calendar> listCalendars();
    public boolean hasCalendar(org.semanticwb.model.Calendar value);

    public void addCalendar(org.semanticwb.model.Calendar value);

    public void removeAllCalendar();

    public void removeCalendar(org.semanticwb.model.Calendar value);

    public org.semanticwb.model.Calendar getCalendar();
}
