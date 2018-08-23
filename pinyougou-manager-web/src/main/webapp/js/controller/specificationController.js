/** 定义控制器层 */
app.controller('specificationController', function($scope, $controller, baseService){

    /** 指定继承baseController */
    $controller('baseController',{$scope:$scope});

    /** 定义搜索JSON对象 */
    $scope.searchEntity = {};
    /** 分页查询 */
    $scope.search = function(page, rows){
        baseService.findByPage("/specification/findByPage", page, 
			rows, $scope.searchEntity)
            .then(function(response){
                $scope.dataList = response.data.rows;
                /** 更新总记录数 */
                $scope.paginationConf.totalItems = response.data.total;
            });
    };

    /** 添加或修改 */
    $scope.saveOrUpdate = function(){
        var url = "save";
        if ($scope.entity.id){
            url = "update";
        }
        /** 发送post请求 {specName : '', specificationOptions : [{},{}]}*/
        baseService.sendPost("/specification/" + url, $scope.entity)
            .then(function(response){
                if (response.data){
                    /** 重新加载数据 */
                    $scope.reload();
                }else{
                    alert("操作失败！");
                }
            });
    };

    /** 显示修改 */
    $scope.show = function(entity){
       $scope.entity = JSON.parse(JSON.stringify(entity));
        // 发送异步请求查询规格选项
        baseService.sendGet("/specification/findOne", "id="
            + entity.id).then(function(response){
                // 获取响应数据[{},{}]
                $scope.entity.specificationOptions = response.data;
        });

    };

    /** 批量删除 */
    $scope.delete = function(){
        if ($scope.ids.length > 0){
            baseService.deleteById("/specification/delete", $scope.ids)
                .then(function(response){
                    if (response.data){
                        $scope.reload();
                    }else{
                        alert("删除失败！");
                    }
                });
        }
    };

    //  {specName : '', specificationOptions : [{},{}]}
    /** 为新增规格选项按钮绑定点击事件 */
    $scope.addTableRow = function () {
        $scope.entity.specificationOptions.push({});
    };
    /** 为删除规格选项按钮绑定点击事件 */
    $scope.deleteTableRow = function (idx) {
        $scope.entity.specificationOptions.splice(idx, 1);
    };

});