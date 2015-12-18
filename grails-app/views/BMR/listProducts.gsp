<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>List of Products</title>
</head>
<body>
  <div class="body">
		Available Products are: <br/> 
		<table width="600">
		<tr>
    		<th>Product Id</th>
    		<th>Product Name</th> 
    		<th>Product Description</th>
    		<th>Product Capacity</th>
    		<th>Product Image</th>
    		
  		</tr>
	<g:each in="${products}" var="product">
			<tr>
				<td align="center"> 
					${product.getProductId()}
				</td>
				<td align="center"> 
					${product.getDisplayName()}
				</td>
				<td align="center">
					${product.getDescription()}
				</td>
				<td align="center"> 
					${product.getCapacity()}
				</td>
				<td align="center"> 
					<img src="${product.getImage()}"/>
				</td>	
			</tr>
		</g:each>
		</table>
  </div>
</body>
</html>