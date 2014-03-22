SECONDS = 1000
MINUTES = 60 * SECONDS
HOURS = 60 * MINUTES
DAYS = 24 * HOURS

names =
  JFK: 'New York'
  MOW: 'Moscow'
  BOS: 'Boston'
  ZRH: 'Zürich'
  LHR: 'London'
  DXB: 'Dubai'

getAirport = (iata) ->
  obj =
    iata: iata
    name: names[iata]

data = []
data.push
  price: 650
  segments: [{
    from: getAirport 'BOS'
    to: getAirport 'DXB'
    fromTime: new Date(Date.now() + 7*DAYS + 3*HOURS)
    toTime: new Date(Date.now() + 7*DAYS + 10*HOURS)
  }]
data.push
  price: 630
  segments: [{
    from: getAirport 'BOS'
    to: getAirport 'LHR'
    fromTime: new Date(Date.now() + 7*DAYS)
    toTime: new Date(Date.now() + 7*DAYS + 4.5*HOURS)
  }, {
    from: getAirport 'LHR'
    to: getAirport 'DXB'
    fromTime: new Date(Date.now() + 7*DAYS + 12*HOURS)
    toTime: new Date(Date.now() + 7*DAYS + 14*HOURS)
  }]
data.push
  price: 670
  segments: [{
    from: getAirport 'BOS'
    to: getAirport 'JFK'
    fromTime: new Date(Date.now() + 7*DAYS)
    toTime: new Date(Date.now() + 7*DAYS + 0.5*HOURS)
  }, {
    from: getAirport 'JFK'
    to: getAirport 'LHR'
    fromTime: new Date(Date.now() + 7*DAYS + 1*HOURS)
    toTime: new Date(Date.now() + 7*DAYS + 5.5*HOURS)
  }, {
    from: getAirport 'LHR'
    to: getAirport 'DXB'
    fromTime: new Date(Date.now() + 7*DAYS + 13*HOURS)
    toTime: new Date(Date.now() + 7*DAYS + 15*HOURS)
  }]
data.push
  price: 620
  segments: [{
    from: getAirport 'BOS'
    to: getAirport 'LHR'
    fromTime: new Date(Date.now() + 7*DAYS + 2*HOURS)
    toTime: new Date(Date.now() + 7*DAYS + 6.5*HOURS)
  }, {
    from: getAirport 'LHR'
    to: getAirport 'DXB'
    fromTime: new Date(Date.now() + 7*DAYS + 13*HOURS)
    toTime: new Date(Date.now() + 7*DAYS + 15*HOURS)
  }]
data.push
  price: 700
  segments: [{
    from: getAirport 'BOS'
    to: getAirport 'ZRH'
    fromTime: new Date(Date.now() + 7*DAYS + 2*HOURS)
    toTime: new Date(Date.now() + 7*DAYS + 7*HOURS)
  }, {
    from: getAirport 'ZRH'
    to: getAirport 'DXB'
    fromTime: new Date(Date.now() + 7*DAYS + 17*HOURS)
    toTime: new Date(Date.now() + 7*DAYS + 19*HOURS)
  }]

@Playover = @Playover ? {}

Search = @Playover.Search
Detail = @Playover.Detail
Results = @Playover.Results

class @Playover.App
  constructor: (sel) ->
    @el = $ sel
    @search = new Search $ '.view-search', @el
    @detail = new Detail $ '.view-detail', @el
    @results = new Results $ '.view-results', @el
    
    @detail.hide()
    @results.hide()
    
    @search.on 'change', =>
      @searching()
      @updateSearch data
    @results.on 'select', @updateSelection
  
  searching: =>
    @results.hide()
    @detail.hide()
  
  updateSearch: (data) =>
    @results.setItems data
    @results.show()
  
  updateSelection: (data) =>
    console.log 'updateSelection!', data
    window.selectedData = data
    @detail.hide()
    
    setTimeout =>
      @detail.setData data
      @detail.show()
    , 50
