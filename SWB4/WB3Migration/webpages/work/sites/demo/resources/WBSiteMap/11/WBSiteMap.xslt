<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/>
<xsl:template match="/mapa">
<LINK href="{@path}images/WBSiteMap.css" rel="stylesheet" type="text/css" ></LINK>
<DIV class="wsm_box">
<TABLE border="0" cellpadding="0" cellspacing="0" width="100%" >
	<TR><TD colspan="2" align="center" class="wsm_title" >
		<xsl:value-of select="@title" />
	</TD></TR>
      	<xsl:for-each select="padre">
		<TR><TD >
			<xsl:if test="@mas ='2'">		
				<A href="{@urlmapa}" >
					<IMG border="0" height="15" width="19" src="{/mapa/@path}images/ico_parent_off.gif" align="middle" ></IMG>
				</A>
			</xsl:if>
			<xsl:if test="@mas ='1'">
				<A href="{@urlmapa}" >
					<IMG border="0" height="15" width="19" src="{/mapa/@path}images/ico_parent_on.gif" align="middle" ></IMG>
				</A>
			</xsl:if>			
			<xsl:if test="@mas ='0'">		
				<IMG border="0" height="15" width="19" src="{/mapa/@path}images/ico_child.gif" align="middle" ></IMG>
			</xsl:if>				
			<A href="{@url}" class="wsm_link" >
				<xsl:value-of select="@nombre" />
			</A>
		</TD></TR>
		<xsl:for-each select="hijo">
			<TR><TD >
				<TABLE border="0" cellpadding="0" cellspacing="0" width="100%" >
				<TR>
					<xsl:for-each select="hijospc">
						<TD width="{@width}" >
							<IMG border="0" height="15" width="19" src="{/mapa/@path}images/line_parent.gif" align="middle" ></IMG>
							<IMG height="5" width="{@width}" border="0" src="{/mapa/@path}images/pixel.gif" ></IMG>
						</TD>
					</xsl:for-each>
					<TD width="19" valign="top" >
						<xsl:if test="@mas ='2'">		
							<A href="{@urlmapa}" >
								<IMG border="0" height="15" width="19" src="{/mapa/@path}images/ico_parent_off.gif" align="middle" ></IMG>
							</A>
						</xsl:if>
						<xsl:if test="@mas ='1'">		
							<A href="{@urlmapa}" >
								<IMG border="0" height="15" width="19" src="{/mapa/@path}images/ico_parent_on.gif" align="middle" ></IMG>
							</A>
						</xsl:if>						
						<xsl:if test="@mas ='0'">		
							<IMG border="0" height="15" width="19" src="{/mapa/@path}images/ico_child.gif" align="middle" ></IMG>
						</xsl:if>
					</TD>
					<TD valign="top" >
						<A href="{@url}" class="wsm_link" >
							<xsl:value-of select="@nombre" />
						</A>
					</TD>
				</TR>
				</TABLE>
			</TD></TR>
		</xsl:for-each>	
	</xsl:for-each>
</TABLE>
</DIV>
</xsl:template>
</xsl:stylesheet>