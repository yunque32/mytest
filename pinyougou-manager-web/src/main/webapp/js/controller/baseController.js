/** 定义基础的控制器 */
app.controller("baseController", function($scope){

    // 定义分页组件需要参数对象
    $scope.paginationConf = {
        currentPage : 1, // 当前页码
        perPageOptions : [10,15,20], // 页码下拉列表框
        totalItems : 0, // 总记录数
        itemsPerPage : 10, // 每页显示记录数
        onChange : function(){ // 当页码发生改变后需要调用的函数
            $scope.reload();
        }
    };

    // 定义重新加载数据方法
    $scope.reload = function(){
        $scope.search($scope.paginationConf.currentPage,
            $scope.paginationConf.itemsPerPage);
    };

    // 定义数组封装用户选择的id
    $scope.ids = [];

    // 为checkbox绑定点击事件
    $scope.updateSelection = function($event,id){

        // $event.target: dom元素

        if ($event.target.checked){

            $scope.ids.push(id);
        }else{
            // 得到该元素在数组中的索引号
            var idx = $scope.ids.indexOf(id);
          //以idx索引开始,删除1个
            $scope.ids.splice(idx,1);
        }
    };


    /** 提取数组中json某个属性，返回拼接字符串 逗号分隔 */
    $scope.jsonArr2Str = function(jsonArrStr, key){

        var jsonArr = JSON.parse(jsonArrStr);

        var resArr = [];

        for (var i = 0; i < jsonArr.length; i++){

            var json = jsonArr[i];
            resArr.push(json[key]);
        }
        return resArr.join(",");
    };

    var json = {name : 'admin', age : 20};
    var key = "name";
    //json[key];

    var obj = new Object();
    obj.add = function(){
        alert("ddd");
    };
    // obj.add();
});