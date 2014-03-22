@Playover = @Playover ? {}

class @Playover.Results extends EventEmitter
  constructor: (@el) ->
    super()
    @items = []
    @results = $ '.results', @el
  
  setItems: (@items) =>
    @results.html ''
    
    groups = _.groupBy @items, (item) ->
      tos = _.map item.segments, (segment) ->
        segment.to.iata
      tos.join '-'
    window.groups = groups
    
    cheapestUnique = _.map groups, (g) ->
      _.sortBy(g, 'price')[0]
    
    window.cheapestUnique = cheapestUnique
    
    _.each cheapestUnique, (item) =>
      result = new Result item
      result.on 'select', =>
        @emit 'select', result.data
      @results.append result.el
  
  show: =>
    @el.show()
    
    document.documentElement.clientHeight
    $('.result', @results).css
      transform: 'translate3d(0, 0, 0)'
  
  hide: =>
    @el.hide()

class Result extends EventEmitter
  constructor: (@data) ->
    @el = $ '<div>'
    @el.addClass 'result'
    @el.css
      transform: 'translate3d(-100%, 0, 0)'
    
    @el.click =>
      @emit 'select'
    
    if @data.segments.length > 1
      @hasLayover()
    else
      @isDirect()
  
  hasLayover: =>
    stops = _.map @data.segments, (segment) -> segment.from.iata
    stops.push @data.segments[@data.segments.length - 1].to.iata
    
    price = $ '<div class="result-price">'
    price.html "$#{@data.price}"
    @el.append price
    
    container = $ '<div class="stop-list">'
    @el.append container
    
    cities = []
    for stop in stops
      cityName = "#{stop}"
      city = $ '<div>'
      city.addClass 'stop-label'
      city.html "<span>#{cityName}</span>"
      cities.push city
      container.append city
    cities[0].addClass 'stop-label-origin'
    cities[cities.length-1].addClass 'stop-label-destination'
  
  isDirect: =>
    price = @data.price
    title = "#{@data.segments[0].from.iata}-#{@data.segments[0].to.iata}"
    titleEl = $ '<h4>'
    titleEl.html "Non-stop #{title} $#{price}"
    
    @el.append titleEl
