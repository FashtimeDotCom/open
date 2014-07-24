/**
 * 将类型为text的输入框的值trim
 * 
 * @return trim后的内容
 */
function trimInputValue() {
	var len = $("input[type='text']").length;
	var obj = $("input[type='text']");
	for ( var i = 0; i < len; i++) {
		obj.get(i).value = obj.get(i).value.trim();
	}
}

/**
 * 去掉所有输入框的disable属性
 */
function cancelInputDisable() {
	var len = $("input").length;
	var obj = $("input");
	for ( var i = 0; i < len; i++) {
		obj[i].disabled = false;
	}
}

function isBlank(str) {
	if (null == str || "" == str) {
		return true;
	}
	str = str.trim();
	return "" == str;
}

String.prototype.trim = function() {
	var str = this;
	str = str.replace(/^\s\s*/, '');
	ws = /\s/;
	i = str.length;
	while (ws.test(str.charAt(--i)))
		;
	return str.slice(0, i + 1);
};

/**
 * 获取上下文路径
 */
function getContextPath() {
	var contextPath = document.location.pathname;
	var index = contextPath.substr(1).indexOf("/");
	contextPath = contextPath.substr(0, index + 1);
	delete index;
	return contextPath;
}

/**
 * 金额大写转换函数
 * 
 * @param n
 *            数值金额
 * @returns 大写金额
 */
function digitChange(n) {
	if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
		return "数据非法";
	var unit = "千百拾亿千百拾万千百拾元角分", str = "";
	n += "00";
	var p = n.indexOf('.');
	if (p >= 0)
		n = n.substring(0, p) + n.substr(p + 1, 2);
	unit = unit.substr(unit.length - n.length);
	for ( var i = 0; i < n.length; i++)
		str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
	return str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(
			/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(
			/^元零?|零分/g, "").replace(/元$/g, "元整");
}

/**
 * 消除元素取值中的字母只保留数字
 * 
 * @param obj
 *            jQuery选择器表示的元素
 */
function ensureNumber(obj) {
	var tmptxt = $(obj).val();
	$(obj).val(tmptxt.replace(/[^\s0-9]/g, ''));
}

/**
 * 判断数值是否为正整数
 * 
 * @param value
 *            数值
 * @returns 是：true；不是：false
 */
function isPositiveNumber(value) {
	return value.indexOf("-") >= 0;
}

/**
 * 判断数值是否超过两位小数
 * 
 * @param value
 *            数值
 * @returns 超过：true；不超过：false
 */
function is2MoreNumAfterDecimal(value) {
	if (value.indexOf(".") < 0) {
		return false;
	}
	return value.indexOf(".") + 3 < value.length;
}

function isValidDate(psYear, psMonth, psDay) {
	if (psYear == null || psMonth == null || psDay == null) {
		return false;
	}
	var sYear = new String(psYear);
	var sMonth = new String(psMonth);
	var sDay = new String(psDay);
//	if (IsValidYear(sYear) == false) {
//		return false;
//	}
//	if (IsValidMonth(sMonth) == false) {
//		return false;
//	}
//	if (IsValidDay(sDay) == false) {
//		return false;
//	}
	var nYear = parseInt(sYear, 10);
	var nMonth = parseInt(sMonth, 10);
	var nDay = parseInt(sDay, 10);
	if (sYear == "" && sMonth == "" && sDay == "") {
		return true;
	}
	if (sYear == "" || sMonth == "" || sDay == "") {
		return false;
	}
	if (nMonth < 1 || 12 < nMonth) {
		return false;
	}
	if (nDay < 1 || 31 < nDay) {
		return false;
	}
	if (nMonth == 2) {
		if ((nYear % 400 == 0) || (nYear % 4 == 0) && (nYear % 100 != 0)) {
			if ((nDay < 1) || (nDay > 29)) {
				return false;
			}
		} else {
			if ((nDay < 1) || (nDay > 28)) {
				return false;
			}
		}
	} else if ((nMonth == 1) || (nMonth == 3) || (nMonth == 5) || (nMonth == 7)
			|| (nMonth == 8) || (nMonth == 10) || (nMonth == 12)) {
		if ((nDay < 1) || (31 < nDay)) {
			return false;
		}
	} else {
		if ((nDay < 1) || (30 < nDay)) {
			return false;
		}
	}
	return true;
}

