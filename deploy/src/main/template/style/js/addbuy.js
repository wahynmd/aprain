(function(){
	ImportJavscript = {
		url:function(url){
			document.write("<script type=\"text/javascript\" src=\""+url+"\"></script>");
		}
	}
})();

ImportJavscript.url('http://style.aprain.com/js/jquery-1.7.2.min.js');


/** 添加一个进货品种时调用 */
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
    	alert('请输入正确的价格！');
    	return ;
    }
	var td6=jQuery('<td></td>');
    td6.append(priceValue);
    var price = jQuery('<input name="price" class="price" type="hidden" value="'+ priceValue + '" />');
    price.appendTo(row);
	
    
    row.append(td1);
    row.append(td2);
    row.append(td3);
    row.append(td4);
    row.append(td5);
    row.append(td6);
    
    row.appendTo(jQuery('#result-table'));
    
    jQuery('#size').val('');
    jQuery('#height').val('');
    jQuery('#grade').val('');
    jQuery('#material').val('');
    jQuery('#num').val('');
    jQuery('#price').val('');
}


/** 点击提交按钮提交表单时调用 */
function createJsonAndSubmit() {
	if( jQuery('#name').val() == '' ) {
		alert('请确认名字已经正确填写！');
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
	
	    dataset.push(obj);
	});
	
	if( (dataset == null) || (dataset.length == 0) ) {
		alert('每笔录入的进货数据至少要包含一个货物品种！');
		return false;
	}
	
	if( confirm('请确认填写的数据无误？') ) {
		formData = JSON.stringify({ 'data': dataset });
		$detailJson.val(formData);
		
		$('#addBuy').submit();
		return true;
	} else {
		return false;
	}
}
