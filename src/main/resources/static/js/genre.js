$('#genreMultiple').selectMultiple({
  selectableHeader: "<input type='text' class='search-input' autocomplete='off' placeholder=''>",
  afterInit: function(ms){
    var that = this,
        $selectableSearch = that.$selectableUl.prev(),
        selectableSearchString = '#'+that.$container.attr('id')+' .ms-elem-selectable';

    that.qs1 = $selectableSearch.quicksearch(selectableSearchString)
    .on('keydown', function(e){
      if (e.which === 40){
        that.$selectableUl.focus();
        return false;
      }
    });
  },
afterSelect: function(values){
this.qs1.cache();
},
afterDeselect: function(values){
this.qs1.cache();
}
});

