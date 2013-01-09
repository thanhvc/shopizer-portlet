<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>				
				
<link href="<c:url value="/resources/css/bootstrap/css/datepicker.css" />" rel="stylesheet"></link>
<script src="<c:url value="/resources/js/bootstrap/bootstrap-datepicker.js" />"></script>				
<script src="<c:url value="/resources/js/jquery.formatCurrency-1.4.0.js" />"></script>
<script src="<c:url value="/resources/js/functions.js" />"></script>

	$(function(){
	
			$('#productSpecialPriceAmount').blur(function() {
				$('#help-price').html(null);
				$(this).formatCurrency({ roundToDecimalPlace: 2, eventOnDecimalsEntered: true, symbol: ''});
			})
			.keyup(function(e) {
					var e = window.event || e;
					var keyUnicode = e.charCode || e.keyCode;
					if (e !== undefined) {
						switch (keyUnicode) {
							case 16: break; // Shift
							case 17: break; // Ctrl
							case 18: break; // Alt
							case 27: this.value = ''; break; // Esc: clear entry
							case 35: break; // End
							case 36: break; // Home
							case 37: break; // cursor left
							case 38: break; // cursor up
							case 39: break; // cursor right
							case 40: break; // cursor down
							case 78: break; // N (Opera 9.63+ maps the "." from the number key section to the "N" key too!) (See: http://unixpapa.com/js/key.html search for ". Del")
							case 110: break; // . number block (Opera 9.63+ maps the "." from the number block to the "N" key (78) !!!)
							case 190: break; // .
							default: $(this).formatCurrency({ colorize: true, negativeFormat: '-%s%n', roundToDecimalPlace: -1, eventOnDecimalsEntered: true, symbol: ''});
						}
					}
				})
			.bind('decimalsEntered', function(e, cents) {
				if (String(cents).length > 2) {
					var errorMsg = '<s:message code="message.price.cents" text="Wrong format" /> (0.' + cents + ')';
					$('#help-special-price').html(errorMsg);
				}
			});


	});


<div class="tabbable">

					<jsp:include page="/common/adminTabs.jsp" />
  					
  					 <div class="tab-content">

    					<div class="tab-pane active" id="catalogue-section">



								<div class="sm-ui-component">
								
				<c:if test="${product.id!=null && product.id>0}">
						<c:set value="${product.id}" var="productId" scope="request"/>
						<jsp:include page="/pages/admin/products/product-menu.jsp" />
				</c:if>		
								
								
				<h3>
						<s:message code="label.product.price" text="Product price" />
				</h3>	
				

				<br/>


				<c:url var="saveProductPrice" value="/admin/products/prices/saveProductPrice.html"/>


				<form:form method="POST" commandName="price" action="${saveProductPrice}">

      							
      				<form:errors path="*" cssClass="alert alert-error" element="div" />
					<div id="store.success" class="alert alert-success" style="<c:choose><c:when test="${success!=null}">display:block;</c:when><c:otherwise>display:none;</c:otherwise></c:choose>"><s:message code="message.success" text="Request successfull"/></div>    

					<div class="control-group">
	                        <label class="required"><s:message code="label.product.price" text="Price"/></label>
	                        <div class="controls">
	                                    <form:input id="productPriceAmount" cssClass="highlight" path="productPrice"/>
	                                    <span id="help-price" class="help-inline"><form:errors path="productPrice" cssClass="error" /></span>
	                        </div>
	                 </div>
					
					
					 <div class="control-group">
                        	<label><s:message code="label.product.price.default" text="Default price"/></label>
                        	<div class="controls">
                                    <form:checkbox path="defaultPrice" />
                        	</div>
                  	</div>
					
					<c:forEach items="${descriptions}" var="description" varStatus="counter">

                        <div class="control-group">

                              <label class="required"><s:message code="label.product.price.name" text="Product price name"/> (<c:out value="${description.language.code}"/>)</label>
                              <div class="controls">
                                          <form:input cssClass="input-large highlight" id="name${counter.index}" path="descriptions[${counter.index}].name"/>
                                          <span class="help-inline"><form:errors path="descriptions[${counter.index}].name" cssClass="error" /></span>
                              </div>
                       </div>	
					
					   <form:hidden path="descriptions[${counter.index}].language.id" />
                       <form:hidden path="descriptions[${counter.index}].language.code" />
					   <form:hidden path="descriptions[${counter.index}].id" />

                  </c:forEach>
                  
                  
                  <div class="control-group">
	                        <label class="required"><s:message code="label.product.price.special" text="Special price"/></label>
	                        <div class="controls">
	                                    <form:input id="productSpecialPriceAmount" cssClass="highlight" path="productSpecialPrice"/>
	                                    <span id="help-special-price" class="help-inline"><form:errors path="productSpecialPrice" cssClass="error" /></span>
	                        </div>
	              </div>
	              
	              <div class="control-group">
	                        <label><s:message code="label.product.price.special.startdate" text="Special start date"/></label>
	                        <div class="controls">
	                        		 <input id="productPriceSpecialStartDate" value="${price.productPriceSpecialStartDate}" class="small" type="text" data-datepicker="datepicker"> 
	                                 <span class="help-inline"></span>
	                        </div>
	              </div>
					

	              <div class="control-group">
	                        <label><s:message code="label.product.price.special.enddate" text="Special end date"/></label>
	                        <div class="controls">
	                        		 <input id="productPriceSpecialEndDate" value="${price.productPriceSpecialEndDate}" class="small" type="text" data-datepicker="datepicker"> 
	                                 <span class="help-inline"></span>
	                        </div>
	              </div>


				  <form:hidden path="productPriceType" />
                  <form:hidden path="id" />
                  <form:hidden path="availability.region" />
                  <form:hidden path="availability.id" />
			
			      <div class="form-actions">

                  		<div class="pull-right">

                  			<button type="submit" class="btn btn-success"><s:message code="button.label.submit" text="Submit"/></button>
                  			
                  		</div>
            	 </div>
 
            	 </form:form>
            	 
            	 
            	 <br/>
            	 

            	 
            	 
	      			     
      					</div>
      					

      			     
      			     


      			     
      			     
    


   					</div>


  					</div>

				</div>		      			     