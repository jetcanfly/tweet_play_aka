$ ->
  ws = new WebSocket $("body").data("ws-url")
  ws.onmessage = (event) ->
    message = JSON.parse event.data

    today = new Date
    hour = today.getHours()
    minute = today.getMinutes()
    second = today.getSeconds()
    prepand = if hour >= 12 then ' PM ' else ' AM '
    hour = if hour >= 12 then hour - 12 else hour
    if hour == 0 and prepand == ' PM '
      if minute == 0 and second == 0
        hour = 12
        prepand = ' Noon'
      else
        hour = 12
        prepand = ' PM'
    if hour == 0 and prepand == ' AM '
      if minute == 0 and second == 0
        hour = 12
        prepand = ' Midnight'
      else
        hour = 12
        prepand = ' AM'
    current_time = 'Current Time : ' + hour + prepand + ' : ' + minute + ' : ' + second
    # $('#stocks').append current_time

    if not message?
      $('#stocks').append 'message is null' + "<br/>" + "<br/>"
    else if not message.text? 
      if second > 54
        $('#stocks').append "nothing new in " + current_time + "<br/>" + "<br/>"
    else
      $('#stocks').append "screen name: " + 
      "<a href='/user/" + message.screen_name+ "'>" + 
      message.screen_name + "</a>" + "<br/>"
      $('#stocks').append "tweet content: " + message.text + "<br/>"
      $('#stocks').append "tweet keyword: " + message.keyword + "<br/>"
      $('#stocks').append "searching time: " + current_time + "<br/>" + "<br/>"

  $("#addsymbolform").submit (event) ->
    event.preventDefault()
    # send the message to watch the stock
    ws.send(JSON.stringify({keyword: $("#addsymboltext").val()}))
    # reset the form
    $("#addsymboltext").val("")