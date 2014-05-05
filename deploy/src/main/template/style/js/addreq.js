(function(){
	ImportJavscript = {
		url:function(url){
			document.write("<script type=\"text/javascript\" src=\""+url+"\"></script>");
		}
	}
})();

ImportJavscript.url('http://style.aprain.com/js/jquery-1.7.2.min.js');


/** 添加一个发货品种时调用 */
function doAdd() {
	var row = jQuery('<tr class="jQueryAdd"></tr>');
	
	
    var sizeValue = jQuery('#size option:selected').val();
    if( (sizeValue == null) || (sizeValue == '') ) {
    	alert('请选择尺寸！');
    	return ;
    }
    var td1 = jQuery('<td></td>');
    td1.append(jQuery('#size option:selected').text());
    var size = jQuery('<input name="size" class="size" type="hidden" value="'+ sizeValue + '" />');
    size.appendTo(row);
    
    
    var heightValue = jQuery('#height option:selected').val();
    if( (heightValue == null) || (heightValue == '') ) {
    	alert('请选择厚度！');
    	return ;
    }
    var td2 = jQuery('<td></td>');
    td2.append(jQuery('#height option:selected').text());
    var height = jQuery('<input name="height" class="height" type="hidden" value="'+ heightValue + '" />');
    height.appendTo(row);
    
    
    var gradeValue = jQuery('#grade option:selected').val();
    if( (gradeValue == null) || (gradeValue == '') ) {
    	alert('请选择等级！');
    	return ;
    }
    var td3 = jQuery('<td></td>');
    td3.append(jQuery('#grade option:selected').text());
    var grade = jQuery('<input name="grade" class="grade" type="hidden" value="'+ gradeValue + '" />');
    grade.appendTo(row);
	
    
    var materialValue = jQuery('#material option:selected').val();
    if( (materialValue == null) || (materialValue == '') ) {
    	alert('请选择材质！');
    	return ;
    }
    var td4 = jQuery('<td></td>');
    td4.append(jQuery('#material option:selected').text());
    var height = jQuery('<input name="material" class="material" type="hidden" value="'+ materialValue + '" />');
    height.appendTo(row);
	
    
    var numReg = /^[1-9]{1}[\d]{0,8}$/;
    var priceReg = /^(([1-9]{1}[0-9]{0,8})|[0]{1})([.]{1}[0-9]{1,3})?$/;
    
    var numValue = jQuery('#num').val();
    if( (numValue == null) || (numValue == '') || (!numReg.test(numValue)) ) {
    	alert('请输入正确的数量！');
    	return ;
    }
	var td5=jQuery('<td></td>');
    td5.append(numValue);
    var num = jQuery('<input name="num" class="num" type="hidden" value="'+ numValue + '" />');
    num.appendTo(row);
	
    
    var priceValue = jQuery('#price').val();
    if( (priceValue == null) || (priceValue == '') || (!priceReg.test(priceValue)) ) {
    	alert('请输入正确的价格！(如果目的地是小库的话，随便写一下即可！)');
    	return ;
    }
	var td6=jQuery('<td></td>');
    td6.append(priceValue);
    var price = jQuery('<input name="price" class="price" type="hidden" value="'+ priceValue + '" />');
    price.appendTo(row);
	
    
    var detailCommentValue = jQuery('#detailComment').val();
	var td7=jQuery('<td></td>');
    td7.append(detailCommentValue);
    var detailCommen = jQuery('<input name="detailComment" class="detailComment" type="hidden" value="'+ detailCommentValue + '" />');
    detailCommen.appendTo(row);
    
    row.append(td1);
    row.append(td2);
    row.append(td3);
    row.append(td4);
    row.append(td5);
    row.append(td6);
    row.append(td7);
    
    row.appendTo(jQuery('#result-table'));
    
    jQuery('#size').val('');
    jQuery('#height').val('');
    jQuery('#grade').val('');
    jQuery('#material').val('');
    jQuery('#num').val('');
    jQuery('#price').val('');
    jQuery('#detailComment').val('');
}


/** 点击提交按钮提交表单时调用 */
function createJsonAndSubmit() {
	if( (jQuery('#name').val() == '') || (jQuery('#expectTime').val() == '') || (jQuery('#phone').val() == '') || (jQuery('#detailAddr').val() == '') ) {
		alert('请确认名字、联系电话、详细地址和期望完成时间都已经正确填写！');
		return false;
	}
	if( (jQuery('#addr option:selected').val() == '') || (jQuery('#isPay option:selected').val() == '') ) {
		alert('请确认目的地和是否付款选项已经选择！(如果目的地是小库的话，是否付款选项随便选一个即可，将不作为凭证！)');
		return false;
	}
	
	var dataset = [],
    	$detailJson = $('#detailJson'),
    	formData;

	$('.jQueryAdd').each(function(i, el) {
	    var $el = $(el),
	        obj = {};
	
	    obj.size = $el.find('.size').val();
	    obj.height = $el.find('.height').val();
	    obj.grade = $el.find('.grade').val();
	    obj.material = $el.find('.material').val();
	    obj.num = $el.find('.num').val();
	    obj.price = $el.find('.price').val();
	    obj.detailComment = $el.find('.detailComment').val();
	
	    dataset.push(obj);
	});
	
	if( (dataset == null) || (dataset.length == 0) ) {
		alert('每笔发货申请至少要包含一个发货品种！');
		return false;
	}
	
	if( confirm('请确认填写的数据无误，并且已在库存页面确认过每个发货品种有足够的库存？') ) {
		formData = JSON.stringify({ 'data': dataset });
		$detailJson.val(formData);
		
		$('#addReq').submit();
		return true;
	} else {
		return false;
	}
}
