<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" encoding="ISO-8859-1" indent="yes"/>
	<xsl:param name="Sigue"/>
        <xsl:param name="title"/>
        <xsl:param name="description"/>
	<xsl:template match="/">
		<script src="/wbadmin/js/calendar.js" ></script>
			
<xsl:comment> ------------------------------------------------------------------------------------------------------------------------------------------------- BODY ---</xsl:comment>
			

				<xsl:text disable-output-escaping="yes">&lt;form name="forma" </xsl:text>action="<xsl:value-of select="$Sigue"/>" method="POST" <xsl:text disable-output-escaping="yes">&gt;</xsl:text>
				<input type="hidden" name="object" value="{/FORMA/@TIPO}" ></input>

<xsl:comment> ---------------------------------------------------------------------------------------------------------------------------- Forma ---</xsl:comment>
					<table cellpadding="0" cellspacing="10" >
					<!--
					<tr><td colspan="2">
						<font face="Verdana, Arial, Helvetica, sans-serif" size="2"><xsl:value-of select="/FORMA/@NOMBRE"/></font>
					</td></tr>-->
			      		<xsl:if test="$title">
			      			<tr><td colspan="2">
				 			<xsl:value-of select="$title"/>
				 		</td></tr>
			      		</xsl:if>
			      		<xsl:if test="$description">
			      			<tr><td colspan="2">
				 			<xsl:value-of select="$description"/><br/><br/>
				 		</td></tr>
			      		</xsl:if>
					<xsl:apply-templates/>
					<tr>
						<td width="50%" align="right" >
							<input type="submit" value="Enviar" ></input>
						</td>
						<td width="50%" align="left" >
							<input type="reset" value="Cancelar" ></input>
						</td>
					</tr>
				</table>
				<xsl:text disable-output-escaping="yes">&lt;/form&gt;</xsl:text>


<xsl:comment> ---------------------------------------------------------------------------------------------------------------------------------- EMPIEZAN FORMAS ---</xsl:comment>
	</xsl:template>
	<xsl:template match="MESSAGE">
	<tr><td colspan="2" >
	<xsl:if test="./@LIGA">
	<xsl:text disable-output-escaping="yes">&lt;a href=&quot;</xsl:text>
	<xsl:value-of select="./@LIGA"/>
	<xsl:text disable-output-escaping="yes">&quot; &gt;</xsl:text>
	</xsl:if><b><font face="Verdana, Arial, Helvetica, sans-serif" size="2">
	<xsl:value-of select="./@TEXTO"/></font></b>
	<xsl:if test="./@LIGA"><xsl:text disable-output-escaping="yes">&lt;/a&gt;</xsl:text></xsl:if>
	</td></tr>
	</xsl:template>
	<xsl:template match="TEXT">
		<tr>
			<td valign="top" WIDTH="150" >
				<font face="Verdana, Arial, Helvetica, sans-serif" size="2">
				<xsl:value-of select="./@ETIQUETA"/>:
				</font>
			</td>
			<td >
				<xsl:text disable-output-escaping="yes">&lt;input type="text" </xsl:text>
				<xsl:if test="./@NOMBRE"> name="<xsl:value-of select="./@NOMBRE"/>"</xsl:if>
				<xsl:if test="./@TAMANO"> size="<xsl:value-of select="./@TAMANO"/>"</xsl:if>
				<xsl:if test="./@TAMANOMAX"> maxlength="<xsl:value-of select="./@TAMANOMAX"/>"</xsl:if>
				<xsl:if test="."> value="<xsl:value-of select="."/>" </xsl:if><xsl:text disable-output-escaping="yes">/&gt;</xsl:text>
				<xsl:if test="./@TIPODATO='Date'"><xsl:text disable-output-escaping="yes">&lt;</xsl:text>a href="javascript:show_calendar('forma.<xsl:value-of select="./@NOMBRE"/>');" <xsl:text disable-output-escaping="yes">&gt;</xsl:text>
					<xsl:text disable-output-escaping="yes">&lt;img src="/wbadmin/resources/DataBaseResource/images/calendar.gif"</xsl:text>
					<xsl:text disable-output-escaping="yes">/&gt;</xsl:text>
					<xsl:text disable-output-escaping="yes">&lt;</xsl:text>/a<xsl:text disable-output-escaping="yes">&gt;</xsl:text></xsl:if>
			</td>
		</tr>
	</xsl:template>
        <xsl:template match="PASSWORD">
		<tr>
			<td valign="top" WIDTH="150" >
				<font face="Verdana, Arial, Helvetica, sans-serif" size="2">
				<xsl:value-of select="./@ETIQUETA"/>:
				</font>
			</td>
			<td >
				<xsl:text disable-output-escaping="yes">&lt;input type="password" </xsl:text>
				<xsl:if test="./@NOMBRE"> name="<xsl:value-of select="./@NOMBRE"/>"</xsl:if>
				<xsl:if test="./@TAMANO"> size="<xsl:value-of select="./@TAMANO"/>" </xsl:if>
				<xsl:if test="./@TAMANOMAX"> maxlength="<xsl:value-of select="./@TAMANOMAX"/>" </xsl:if>
				<xsl:if test="."> value="<xsl:value-of select="."/>" </xsl:if>
				<xsl:text disable-output-escaping="yes">/&gt;</xsl:text>
			</td>
		</tr>
		<xsl:if test="./@VERIFICAR='SI'">
				<tr>
			<td valign="top" WIDTH="150" >
				<font face="Verdana, Arial, Helvetica, sans-serif" size="2">Re-
				<xsl:value-of select="./@ETIQUETA"/>:
				</font>
			</td>
			<td >
				<xsl:text disable-output-escaping="yes">&lt;input type="password" </xsl:text>
				<xsl:if test="./@NOMBRE"> name="_<xsl:value-of select="./@NOMBRE"/>"</xsl:if>
				<xsl:if test="./@TAMANO"> size="<xsl:value-of select="./@TAMANO"/>" </xsl:if>
				<xsl:if test="./@TAMANOMAX"> maxlength="<xsl:value-of select="./@TAMANOMAX"/>" </xsl:if>
				<xsl:if test="."> value="<xsl:value-of select="."/>" </xsl:if>
				<xsl:text disable-output-escaping="yes">/&gt;</xsl:text>
			</td>
		</tr>
		</xsl:if>
	</xsl:template>
	<xsl:template match="TEXTAREA">
		<tr>
			<td valign="top" WIDTH="150" >
				<font face="Verdana, Arial, Helvetica, sans-serif" size="2">
				<xsl:value-of select="./@ETIQUETA"/>:
				</font>
			</td>
			<td >
				<xsl:text disable-output-escaping="yes"> &lt;textarea </xsl:text>
				<xsl:if test="./@NOMBRE">name="<xsl:value-of select="./@NOMBRE"/>" </xsl:if>
				<xsl:if test="./@RENGLONES">rows="<xsl:value-of select="./@RENGLONES"/>" </xsl:if>
				<xsl:if test="./@COLUMNAS">cols="<xsl:value-of select="./@COLUMNAS"/>" </xsl:if>
				<xsl:text disable-output-escaping="yes"> &gt;</xsl:text>
				<xsl:if test="."><xsl:value-of select="."/></xsl:if>
				<xsl:text disable-output-escaping="yes">&lt;/textarea&gt;</xsl:text>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="FILE">
		<tr>
			<td valign="top" WIDTH="150" >
				<font face="Verdana, Arial, Helvetica, sans-serif" size="2">
				<xsl:value-of select="./@ETIQUETA"/>:
				</font>
			</td>
			<td >
				<input type="file" name="{./@NOMBRE}" ></input>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="HIDDEN">
		<input type="hidden" name="{./@NOMBRE}" value="{./@VALOR}" ></input>
	</xsl:template>
	<xsl:template match="SELECT">
		<tr>
			<td valign="top" WIDTH="150" >
				<font face="Verdana, Arial, Helvetica, sans-serif" size="2">
				<xsl:value-of select="./@ETIQUETA"/>:
				</font>
			</td>
			<td >
				<xsl:text disable-output-escaping="yes">&lt;select </xsl:text>
				<xsl:if test="./@NOMBRE">name="<xsl:value-of select="./@NOMBRE"/>" </xsl:if>
				<xsl:if test="./@RENGLONES">size="<xsl:value-of select="./@RENGLONES"/>"</xsl:if>
				<xsl:value-of select="./@SELECCION"/>
				<xsl:if test="./@ANIDADO"> onchange="forma.submit()"</xsl:if>
				<xsl:text disable-output-escaping="yes"> &gt;</xsl:text>
					<option value="">Seleccione</option>
				<xsl:for-each select="ELEMENTO">
					<xsl:sort select="./@ETIQUETA"/>
					<xsl:choose>
						<xsl:when test="./@INICIAL='SI'">
							<option value="{./@VALOR}" selected="">
								<xsl:value-of select="./@ETIQUETA"/>
							</option>
						</xsl:when>
						<xsl:otherwise>
							<option value="{./@VALOR}">
								<xsl:value-of select="./@ETIQUETA"/>
							</option>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:for-each>
				<xsl:text disable-output-escaping="yes">&lt;/select&gt;</xsl:text>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="RADIOSET">
		<xsl:variable name="nombre" select="./@NOMBRE"/>
		<tr>
			<td valign="top" WIDTH="150" >
				<font face="Verdana, Arial, Helvetica, sans-serif" size="2">
				<xsl:value-of select="./@ETIQUETA"/>:
				</font>
			</td>
			<td >
				<xsl:choose>
					<xsl:when test="./@TIPO='CAJA'">
						<xsl:for-each select="ELEMENTO">
							<xsl:choose>
								<xsl:when test="./@INICIAL='SI'">
									<input type="checkbox" name="{$nombre}" value="{./@VALOR}" checked="" ></input>
									<xsl:value-of select="./@ETIQUETA"/>
									<xsl:if test="../@LAYOUT='VERTICAL'">
										<br/>
									</xsl:if>
								</xsl:when>
								<xsl:otherwise>
									<input type="checkbox" name="{$nombre}" value="{./@VALOR}" ></input>
									<xsl:value-of select="./@ETIQUETA"/>
									<xsl:if test="../@LAYOUT='VERTICAL'">
										<br/>
									</xsl:if>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:for-each>
					</xsl:when>
					<xsl:otherwise>
						<xsl:for-each select="ELEMENTO">
							<xsl:choose>
								<xsl:when test="./@INICIAL='SI'">
									<input type="radio" name="{$nombre}" value="{./@VALOR}" checked="" ></input>
									<xsl:value-of select="./@ETIQUETA"/>
									<xsl:if test="../@LAYOUT='VERTICAL'">
										<br/>
									</xsl:if>
								</xsl:when>
								<xsl:otherwise>
									<input type="radio" name="{$nombre}" value="{./@VALOR}" ></input>
									<xsl:value-of select="./@ETIQUETA"/>
									<xsl:if test="../@LAYOUT='VERTICAL'">
										<br/>
									</xsl:if>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:for-each>
					</xsl:otherwise>
				</xsl:choose>
			</td>
		</tr>
	</xsl:template>
</xsl:stylesheet>
