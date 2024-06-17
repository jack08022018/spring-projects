
$(document).ready(function () {
		
//	http://localhost:8080/vnHomepageFront/api/getPlpReportCardInfo.kps?memberCode=57090065&levelCode=682&learningYearCode=2020&semesterGbn=01&missionSeq=2&token=3E97B570-BCCF-4216-86D5-F899B594A6A6
	
	var searchData;
	getReportCardData();
	function getReportCardData() {
		$('.heading-tab-mission').remove();
		$('.mission-content').remove();
		$('.mission-tab').remove();
		
		var params = new FormData();
		params.append("token", PARAMS.token);
		params.append("langCode", PARAMS.langCode);
		params.append("levelCode", PARAMS.levelCode);
		params.append("learningYearCode", PARAMS.learningYearCode);
		params.append("semesterGbn", PARAMS.semesterGbn);
		params.append("memberCode", PARAMS.memberCode);
		params.append("missionSeq", PARAMS.missionSeq);
		
		$.ajax({
			type: "POST",
	        enctype: 'multipart/form-data',
	        url: CONTEXT_PATH + '/api/getReportCardData.json',
	        data: params,
	        processData: false,
	        contentType: false,
	        cache: false,
	        timeout: 600000,
	        success: function (response) {
        		try {
        			if(response.actionStatus == 'error') {
        				throw new Error(response.message);
        			}
        			searchData = response.data;
	    			loadStudentInfo(searchData.studentInfo);
	    			loadDataChartOverall(searchData.overallData);
	    			buildAllMission();
	    			console.log(searchData)
				}catch(e) {
		    		handleException(e);
		    	}
	        },
	        error: function (response) {
	        	alert(response.statusText);
	        },
			failure: function (response) {
				alert(response.statusText);
	        }
		});
	}
	var COMPLETED_STATUS = 'CP';
	function buildAllMission() {
		var showOveral = true;
		var missionList = searchData.overallData.missionList;
		missionList.forEach(function(s) {
			var data = searchData[s.missionName].dataSubject;
			if(data.length == 0) {
				
				return;
			}
			
//			calculate total
			var totalList = searchData[s.missionName].total;
			totalList.forEach(function(t) {
				var dataEachType = _.filter(data, {missionType: t.missionType});
				t.totalNumber = _.sumBy(dataEachType, 'totalNumber');
				t.correctNumber = _.sumBy(dataEachType, 'correctNumber');
				t.avgClass = formatNumber((_.sumBy(dataEachType, 'avgClass'))/dataEachType.length, 1);
				t.avgPoly = formatNumber((_.sumBy(dataEachType, 'avgPoly'))/dataEachType.length, 1);
				
				var percentPointTotal = _.sumBy(dataEachType, 'percentPoint');
				t.percentPoint = formatNumber(percentPointTotal/dataEachType.length, 1);
			})
			data.forEach(function(d) {
				d.percentPoint = formatNumber(d.percentPoint, 1);
				d.avgClass = formatNumber(d.avgClass, 1);
				d.avgPoly = formatNumber(d.avgPoly, 1);
			})
			var missionTypeList = _.uniqBy(totalList, 'missionType');
			data = _.filter(data, missionTypeList[0].missionType);
			var totalRow = _.filter(totalList, missionTypeList[0].missionType);
			
//			build html
			buildMissionTab(s.missionSeq, data, totalRow, missionTypeList);
			
//			set duration
			s.assignDate = formatDate(s.assignDate);
			s.finishedDate = formatDate(s.finishedDate);
			$('#m' + s.missionSeq + '-duration').text(s.assignDate + ' - ' + (s.finishedDate != '' ? s.finishedDate : '...'));
			showOveral = false;
		})
		if(showOveral){
			var idTab = '#overall';
			$(".nav-menu a, .heading-tab a").removeClass("active");
			$('#overallTabId').addClass("active").toggleClass("open");
			$(".heading-tab a[href='"+idTab+"']").addClass("active");
			$(".content-group").removeClass("show");
			$(idTab).addClass("show").toggleClass("open");
			$(".content-group").each(function(){
				if($('#overallTabId').hasClass("show") && $(this).hasClass("open")){
				}else{
					$('#overallTabId').removeClass("open");
					var idTabRemove = $('#overallTabId').attr("id");
					$(".heading-tab a[href='#"+idTabRemove+"'], .nav-menu a[href='#"+idTabRemove+"']").removeClass("open");	
				}
			});
		}
	}
	
	$('body').on('change', 'select', function() {
		var missionSeq = $(this).attr('missionSeq');
		var idHtml = $(this).attr('idHtml');
		var missionType = $(this).val() == 'Challenge' ? 'A' : 'R';
		$('#m' + missionSeq + '-GEDE').empty();
		$('#m' + missionSeq + '-GEDE').append('<li><button class="btn-tab btn-general active" data-id="general">General</button></li>');
		$('#m' + missionSeq + '-GEDE').append('<li><button class="btn-tab" data-id="details">Details</button></li>');
		$('#' + idHtml).empty();
		buildMissionContentHtml(idHtml, missionSeq, missionType);
		loadReportCardSet();
	});
	
	function loadStudentInfo(info) {
		$('.student-info-content').empty();
		var html = ''
			+ 	'<div class="row">'
			+		'<div class="col-sm-7 col-xs-12"><p><strong>Name</strong><span>' + info.memberName + '</span></p></div>'
			+		'<div class="col-sm-5 col-xs-12"><p><strong>Class</strong><span>' + info.className + '</span></p></div>'
			+	'</div>'
			+	'<div class="row">'
			+		'<div class="col-sm-7 col-xs-12"><p><strong>Level</strong><span>' + info.levelName + '</span></p></div>'
			+		'<div class="col-sm-5 col-xs-12"><p><strong>Campus</strong><span>' + info.clientName + '</span></p></div>'
			+	'</div>';
		$('.student-info-content').append(html);
	}
	
	function buildMissionTab(missionSeq, data, total, missionTypeList) {
		var activeContent = missionSeq == searchData.missionSelect ? 'show open' : '';
		var missionType = missionTypeList[0].missionType;
		var typeListHtml = '';
		missionTypeList.forEach(function(s) {
			typeListHtml += '<option>' + s.missionTypeName + '</option>';
		})
		var html = ''
			+ '<h5 class="heading-tab heading-tab-mission"><a href="#m' + missionSeq + '">Mission ' + missionSeq + '</a></h5>'
			+ '<div id="m' + missionSeq + '" class="content-group mission-content ' + activeContent + '">'
			+ 		'<div class="head-content">'
			+ 			'<div class="row">'
			+ 				'<div class="col-sm-8 col-xs-12">'
			+ 					'<div class="heading-box">'
			+ 						'<label>Type</label>'
			+ 						'<div class="select-box">'
			+     						'<select class="select-picker selectType" missionSeq="' + missionSeq + '" idHtml="m' + missionSeq + '-content">'
			+ 							  	typeListHtml
			+ 							'</select>'
			+ 						'</div>'
			+ 					'</div>'
			+ 					'<div class="heading-box">'
			+ 						'<label>Duration <strong id="m' + missionSeq + '-duration">01 Sep 2019 - 31 Sep 2019</strong></label>'
			+ 					'</div>'
			+ 				'</div>'
			+ 				'<div class="col-sm-4 col-xs-12">'
			+ 					'<ul id="m' + missionSeq + '-GEDE" class="nav-content">'
			+ 						'<li><button class="btn-tab btn-general active" data-id="general">General</button></li>'
			+ 						'<li><button class="btn-tab" data-id="details">Details</button></li>'
			+ 					'</ul>'
			+ 				'</div>'
			+ 			'</div>'
			+ 		'</div>'
			+		'<div id="m' + missionSeq + '-content" class="content-main">'
//						buildMissionContentHtml
			+		'</div>'		
			+	 '</div>'
            + '</div>';
		
		var activeTab = missionSeq == searchData.missionSelect ? 'active open' : '';
		$('.nav-menu').append('<li class="mission-tab"><a href="#m' + missionSeq + '" class="mission mission-' + missionSeq + ' ' + activeTab + '">Mission 1</a></li>');
		$('.content').append(html);
		buildMissionContentHtml('m' + missionSeq + '-content', missionSeq, missionType);
		
		loadReportCard();
	}
	
	function buildMissionContentHtml(idHtml, missionSeq, missionType) {
		var missionName = (_.find(searchData.overallData.missionList, {missionSeq: parseInt(missionSeq)})).missionName;
		var missionData = searchData[missionName];
		var dataSubject = _.filter(searchData[missionName].dataSubject, {missionType: missionType});
		var total = _.find(missionData.total, {missionType: missionType});
		var dataSkill = _.filter(missionData.dataSkill, {missionType: missionType});
		var subjectHtml = '';
		dataSubject.forEach(function(s, index) {
			subjectHtml += '<li><a href="#m' + missionSeq + '-' + s.subjectCode + '" class="btn-tab-list ' + (index == 0 ? 'active' : '') + '">' + s.subjectName + '</a></li>';
		})
		var html = ''
			+	'<div data-tab="general" class="group-main show box-scroll mScrollbar pdlr22">'
			+		'<h4 class="title text-center">GENERAL ANALYSIS</h4>'
			+		'<div class="table-responsive">'
			+			getGeneralGridHtml(dataSubject, total)
			+		'</div>'
			+		'<div id="m' + missionSeq + '-ge" class="chart-colunms char-1"></div>'
			+	'</div>'
			+	'<div data-tab="details" class="group-main">'
			+		'<ul class="list-nav">'
			+			subjectHtml
			+		'</ul>'
			+		'<div class="group-tab">'		
			+			getDetailGridHtml(missionSeq, dataSkill, dataSubject)
			+		'</div>'
			+	'</div>';
		$('#' + idHtml).append(html);
		loadDataChartMission(missionSeq, dataSubject, dataSkill);
	}
	
	function getGeneralGridHtml(data, total) {
		var html = '';
		data.forEach(function(s) {
			var status = s.assignStatus;
			html += ''
				+	'<tr>'
				+		'<td>' + s.subjectName + '</td>'
				+		'<td>' + s.correctNumber + '/' + s.totalNumber + '</td>'
				+		'<td>' + s.percentPoint + '</td>'
				+		'<td>' + s.avgClass + '</td>'
				+		'<td>' + s.avgPoly + '</td>'
				+		'<td colspan="3"><div class="wrap-char"><div class="wrap-char-inner" data-start="' + s.rankStart + '" data-end="' + s.rankEnd + '"></div></div></td>'
				+		'<td>' + s.rankPoint + '</td>'
				+	'</tr>';
		})
		html += ''
			+	'<tr>'
			+		'<td class="td-total">' + total.subjectName + '</td>'
			+		'<td class="td-total">' + total.correctNumber + '/' + total.totalNumber + '</td>'
			+		'<td class="td-total">' + total.percentPoint + '</td>'
			+		'<td class="td-total">' + total.avgClass + '</td>'
			+		'<td class="td-total">' + total.avgPoly + '</td>'
			+		'<td class="td-total" colspan="3"><div class="wrap-char"><div class="wrap-char-inner" data-start="' + total.rankStart + '" data-end="' + total.rankEnd + '"></div></div></td>'
			+		'<td>' + total.rankPoint + '</td>'
			+	'</tr>';
		return ''
			+	'<table class="table table-bordered text-center">'
			+		'<thead>'
			+			'<tr>'
			+			 	'<th rowspan="2">Subject</th>'
			+				'<th rowspan="2">Correct</th>'
			+				'<th rowspan="2">Score</th>'
			+				'<th colspan="2">Averages</th>'
			+				'<th colspan="3">Rank</th>'
			+				'<th rowspan="2">%</th>'
			+			'</tr>'
			+			'<tr>'
			+				'<th>Class</th>'
			+				'<th>POLY</th>'
			+				'<th>BA</th>'
			+				'<th>A50%</th>'
			+				'<th>AA</th>'
			+			'</tr>'
			+		'</thead>'
			+		'<tbody>'
			+			html
			+		'</tbody>'
			+	'</table>'
	}
	
	function getDetailGridHtml(missionSeq, dataSkill, dataSubject) {
		var html = '';
		dataSubject.forEach(function(d) {
			var htmlGrid = '';
			var skillBySubject = _.filter(dataSkill, {subjectCode: d.subjectCode});
			skillBySubject.forEach(function(s, index) {
				s.no = ++index;
				htmlGrid += ''
					+	'<tr>'
					+		'<td>' + s.no + '</td>'
					+		'<td>' + s.skillName + '</td>'
					+		'<td>' + s.correctNumber + '/' + s.totalNumber + '</td>'
					+		'<td>' + s.percentPoint + '</td>'
					+		'<td>' + s.avgClass + '</td>'
					+		'<td>' + s.avgPoly + '</td>'
					+		'<td colspan="3"><div class="wrap-char"><div class="wrap-char-inner" data-start="' + s.rankStart + '" data-end="' + s.rankEnd + '"></div></div></td>'
					+		'<td>' + s.rankPoint + '</td>'
					+	'</tr>';
			})
			html += ''
				+	'<div id="m' + missionSeq + '-' + d.subjectCode + '" class="text-content box-scroll mScrollbar inShow">'
				+		'<h4 class="title text-center pt15">STRENGTH &amp; WEAKNESS ANALYSIS</h4>'
				+		'<div class="table-responsive">'
				+			'<table class="table table-bordered text-center">'
				+				'<thead>'
				+					'<tr>'
				+						'<th rowspan="2">No</th>'
				+						'<th rowspan="2">Skills</th>'
				+						'<th rowspan="2">Correct</th>'
				+						'<th colspan="3">Averages</th>'
				+						'<th colspan="3">Rank</th>'
				+						'<th rowspan="2">%</th>'
				+					'</tr>'
				+					'<tr>'
				+						'<th>Student</th>'
				+						'<th>Class</th>'
				+						'<th>POLY</th>'
				+						'<th>BA</th>'
				+						'<th>A50%</th>'
				+						'<th>AA</th>'
				+					'</tr>'
				+				'</thead>'
				+				'<tbody>'
				+					htmlGrid
				+				'</tbody>'
				+			'</table>'
				+		'</div>'
				+		'<div id="m' + missionSeq + '-' + d.subjectCode + '-chart" class="chart-colunms char-2"></div>'
				+	'</div>';
		})
		return html;
	}
	
	function loadDataChartOverall(overallData) {
//		chart top
		var missionList = overallData.missionList;
		var dataTop = overallData.subjectList;
		var dataBot = overallData.reviewChallengeList;
		var subjectList = _.uniqBy(dataTop, 'subjectCode');
		var category = _.map(subjectList, 'subjectName');
		var dataChartTop = [];
		missionList.forEach(function(m) {
			var dataMission = [];
			subjectList.forEach(function(s) {
				var info = _.find(dataTop, {missionSeq: m.missionSeq, subjectCode: s.subjectCode})
				dataMission.push(info == undefined ? null : info.percentPoint);
			})
			dataChartTop.push({name: m.missionName, data: dataMission});
		})
		myChartColumCustom("chartColum", category, dataChartTop);
		
//		chart review challenge
		var dataChartBot = [];
		var dataMission = [];
		subjectList.forEach(function(s) {
			var info = _.find(dataBot, {missionType: 'R', subjectCode: s.subjectCode})
//			if(info != undefined) {
				dataMission.push(info == undefined ? 0 : info.percentPoint);
//			}
		})
		if(dataMission.length > 0) {
			dataChartBot.push({name: 'Review', data: dataMission});
		}
		
		dataMission = [];
		subjectList.forEach(function(s) {
			var info = _.find(dataBot, {missionType: 'A', subjectCode: s.subjectCode})
//			if(info != undefined) {
				dataMission.push(info == undefined ? 0 : info.percentPoint);
//			}
		})
		if(dataMission.length > 0) {
			dataChartBot.push({name: 'Challenge', data: dataMission});
		}
		var listColor = ['#46bd18', '#00b0d0'];
		myChartColumCustom("chartColum2", category, dataChartBot, listColor);
	}
	function loadDataChartMission(missionSeq, dataSubject, dataSkill) {
//		load chart general
		var dataStudent = [];
		var dataClass = [];
		var dataPoly = [];
		dataSubject.forEach(function(s) {
			dataStudent.push(s.percentPoint);
			dataClass.push(s.avgClass);
			dataPoly.push(s.avgPoly);
		})
		var dataChartGe = [
			{name: 'Student', data: dataStudent}, 
			{name: 'Class', data: dataClass},
			{name: 'POLY', data: dataPoly}
		];
		var cateChartGe = _.map(dataSubject, 'subjectName');
		myChartColumCustom('m' + missionSeq + '-ge', cateChartGe, dataChartGe);
		
//		load chart detail
		dataSubject.forEach(function(s) {
			var dataStudent = [];
			var dataClass = [];
			var dataPoly = [];
			var skillBySubject = _.filter(dataSkill, {subjectCode: s.subjectCode});
			skillBySubject.forEach(function(k) {
				dataStudent.push(k.percentPoint);
				dataClass.push(k.avgClass);
				dataPoly.push(k.avgPoly);
			})
			var dataChartDe = [
				{name: 'Student', data: dataStudent},
				{name: 'Class', data: dataClass},
				{name: 'POLY', data: dataPoly}
			];
			var cateChartDe = _.map(skillBySubject, 'skillName');
			myChartColumBar('m' + missionSeq + '-' + s.subjectCode + '-chart', cateChartDe, dataChartDe);
		})
		
	}
	
//	END
});
