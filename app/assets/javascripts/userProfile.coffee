$ ->
  ws = new WebSocket $("body").data("ws-url")
  ws.onmessage = (event) ->
    message = JSON.parse event.data
    $('#stocks').append "name: " + message.name + "<br/>"
    $('#stocks').append "id: " + message.id_str + "<br/>"
    $('#stocks').append "location: " + message.location + "<br/>"
    $('#stocks').append "description: " + message.description + "<br/>"
    $('#stocks').append "url: " + message.url + "<br/>" + "<br/>"
    for text, index in message.texts
      $('#stocks').append "tweet" + (index + 1) + ": " + text + "<br/>"


  $("#addsymbolform").submit (event) ->
    event.preventDefault()
    # send the message to watch the stock
    ws.send(JSON.stringify({keyword: $("#addsymboltext").val()}))
    # reset the form
    $("#addsymboltext").val("")