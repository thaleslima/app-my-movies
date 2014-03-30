var id;
//var urlws = "http://192.168.0.101:8080/ws-movie/services/";
var urlws = "http://localhost:8080/ws-movie/services/";
	
$(document).ready(function(){
	
  $("#btn-reflesh").click(function(){
  	 refleshFilmes();
  });
	
  carregarFilmes();	
  $("#searchinput1").keyup(function() {
	  carregarFilmes();  
  });
   
  $(".opcoes-ul").hide();
  $("#mostra-opcoes").click(function(){
  	  $(".opcoes-ul").toggle(1);
  });
  
  $("input:radio[name=visto],input:radio[name=ordenar],input:radio[name=agrupar]").bind( "change", function(event, ui) {
	  carregarFilmes();	
  });
  
});

function refleshFilmes()
{
	$.ajax({
		type: 'GET',
		timeout: 3000,
		url: 'http://localhost:8080/ws-movie/services/retornaTodos/X/Id',
		contentType: 'application/json; charset=utf-8',
		dataType: 'json',
		success: function (msg) {
			localStorage.filmes=JSON.stringify(msg);
			carregaFilmes(JSON.parse(localStorage.filmes));
			location.reload();
		},
		error: function(a, b, c)
		{
			$(".page-wait").hide();
		},
		beforeSend: function(){
			$(".page-wait").show();
	    }
	});
}

function carregaresumo()
{
	$("#visto").on("slidestop", function(event, ui) {
		atualizarVisto();
	});
	
	
	if(localStorage.filmes)
	{
		var filmes = JSON.parse(localStorage.filmes);
		
		$.each(filmes, function(i, data) {
		    if (data.id == id) {
		    	$("#titulo-filme").html(data.titulo);
				$("#ano").html(data.ano);
				$("#audiooriginal").html(data.audioOriginal);
				$("#genero").html(data.genero.descricao);
				$("#tipoaudio").html(data.tipoAudio);
				$("#sinopse").html(data.sinopse);
				$("#visto").val(data.visto);
				return;
		    }
		});
	
	}
	else	
	{
		$.getJSON(urlws +'getFilme/'+id, function(data) {
			$("#titulo-filme").html(data.titulo);
			$("#ano").html(data.ano);
			$("#audiooriginal").html(data.audioOriginal);
			$("#genero").html(data.genero.descricao);
			$("#tipoaudio").html(data.tipoAudio);
			$("#sinopse").html(data.sinopse);
			$("#visto").val(data.visto);
			$('#visto').slider('refresh');
		});
	}
	//$('#visto').slider('refresh');
}


function atualizarVisto()
{
	$.ajax({
        type: "GET",
        url: urlws +"atualizaVisto/" + id +"/" + $("#visto").val(),
        success: function(data){
            carregarFilmes($("#searchinput1").val());
        },
        error: function(){
            //alert('updateWine error: ' + textStatus);
        }
    });
}


function setid(a)
{
	id = a;
}

function carregarFilmes()
{
	
	var texto = $('#searchinput1').val();
	var visto = $('input:radio[name=visto]:checked').val();
	var ordenar = $('input:radio[name=ordenar]:checked').val();
	
	//var dataAtual = $.now();
	
	//alert(dataAtual);
	
	//dataAtual = dataAtual - localStorage.horaupdate;
	
	//alert(dataAtual);
	
	//jQuery.ajax({
   //     url: urlws +'retornaTodos/X/ID',
        //dataType: "json", //Tipo de Retorno
   //     success: function(json){
   //        localStorage.filmes=JSON.stringify(json);
   //        localStorage.horaupdate=$.now();
    //    },
    //    error:function()
    //    {
        	//localStorage.horaupdate=$.now();
        	//1361317194030
        	//1361317234551
        	//1361317849292
        	//1361320823772
        	//      3612269
     //   }
     //});
	
	//$.getJSON(urlws +'retornaTodos/'+visto+'/'+ordenar+'/'+texto, function(data) {
	//	alert("ss");
	//	carregaFilmes(data);
	//}).error(function() { carregaFilmes(JSON.parse(localStorage.filmes)); });
	//}).error(function() {  });
	
	//$.getJSON('http://192.168.0.101:8080/ws-movie/services/retornaTodos/X/Id', function(data) {
	//  	alert(data);
	//});
	
	if(localStorage.filmes)
	{
		carregaFilmes(JSON.parse(localStorage.filmes));
	}
	else
	{
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8080/ws-movie/services/retornaTodos/X/Id',
			contentType: 'application/json; charset=utf-8',
			dataType: 'json',
			success: function (msg) {
				localStorage.filmes=JSON.stringify(msg);
				carregaFilmes(JSON.parse(localStorage.filmes));
			},
			error: function(a, b, c)
			{
			}
		});
	}
}


function carregaFilmes(data)
{
	var visto = $('input:radio[name=visto]:checked').val();
	var ordenar = $('input:radio[name=ordenar]:checked').val();
	var agrupar = $('input:radio[name=agrupar]:checked').val();
	var genero = "";
	var sTitulo = "";
	
	list = "";
	$.each(data, function(i,row){
		if (visto == row.visto || visto == "X")
		{
			sTitulo = row.titulo;
			
			if(ordenar == "genero" && agrupar == "S")
			{
				if(genero != row.genero.descricao)
				{
					list += '<li data-role="list-divider" role="heading">';
					list += row.genero.descricao;
					list += '	</li>';
					genero = row.genero.descricao;
				}
			}
			
			if(row.visto == "S")
			{
				list += '<li data-theme="b">';
			}
			else
			{
				list += '<li data-theme="c">';
			}
			list += '<a href="resumo.html" data-transition="slide" onclick="setid('+ row.id +');">'+sTitulo+'</a></li>';
		}
	});
	
	if(ordenar == "id" || agrupar == "N" || ordenar == "genero")
	{
		$("#listview2").hide();
		$("#listview").show();
		$_listview = $("#listview");
	}
	else
	{
		$("#listview").hide();
		$("#listview2").show();
		$_listview = $("#listview2");
	}
	
	$_listview.html(list);	
	$_listview.listview('refresh');
}


function carregarDados(texto)
{
	var list;
	var $_listview;
	
	$.ajax({
        type: "GET",
        url: "Filmes.xml",
        dataType: "xml",
        success: function (xml) {
        	
        	list = "";
        	
            $(xml).find('Filme').each(function () {
                var sTitulo = $(this).find('Titulo').text();
                
                if(row.visto == "S")
        		{
        			list += '		<li data-theme="b">';
        		}
        		else
        		{
        			list += '		<li data-theme="c">';
        		}
                
        		list += '		<a href="#" data-transition="slide">';
        		list += '		' + sTitulo + texto;
        		list += '		</a>';
        		list += '		</li>';
            });
            
            $_listview = $("#listview");
            $_listview.html(list);	
    		$_listview.listview('refresh');
        },
        error: function () {
            alert("Ocorreu um erro inesperado durante o processamento.");
        }
    });
}



