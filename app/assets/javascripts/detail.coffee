@Playover = @Playover ? {}

cityInformation = @cityInformation

class @Playover.Detail extends EventEmitter
  constructor: (@el) ->
    super()
    @details = $ '.details', @el
  
  setData: (@data) ->
    @details.html ''
    
    stops = _.map @data.segments, (segment) ->
      iata: segment.from.iata
      fromTime: segment.fromTime
      toTime: segment.toTime
      playover: false
      duration: 0
    
    last = @data.segments[@data.segments.length - 1]
    stops.push
      iata: last.to.iata
      fromTime: last.toTime
      toTime: last.toTime
      playover: false
      duration: 0
    
    for i in [1..stops.length-1] by 1
      stops[i].duration = Math.abs stops[i].fromTime.getTime() - stops[i-1].toTime.getTime()
    
    sorted = _.sortBy stops, (stop) -> -stop.duration
    longestLayover = sorted[0]
    
    info = _.find cityInformation, (city) ->
      city.iata == longestLayover.iata
    
    hours = Math.round longestLayover.duration / 1000 / 60 / 60
    
    title = $ '<h3>'
    title.html "Explore #{info.title}"
    @details.append title
    
    time = $ '<h4>'
    time.html "#{hours} hours"
    @details.append time
  
  show: =>
    @el.show()
  
  hide: =>
    @el.hide()
