app.controller('sellerController', function($scope, $controller, baseService){

    $controller('baseController',{$scope:$scope});

    $scope.saveOrUpdate = function(){

        baseService.sendPost("/seller/save", $scope.seller)
            .then(function(response){
                if (response.data){
                    location.href = "/shoplogin.html";
                }else{
                    alert("操作失败！");
                }
            },function () {
                alert("请求失败");
            });
    };

    /** 定义搜索对象 */
    $scope.searchEntity = {};
    /** 分页查询 */
    $scope.search = function(page, rows){
        baseService.findByPage("/seller/findByPage", page,
            rows, $scope.searchEntity)
            .then(function(response){
                $scope.dataList = response.data.rows;
                /** 更新总记录数 */
                $scope.paginationConf.totalItems = response.data.total;
            });
    };

    /** 显示修改 */
    $scope.show = function(entity){
        $scope.entity = JSON.parse(JSON.stringify(entity));
    };

    /** 批量删除 */
    $scope.delete = function(){
        if ($scope.ids.length > 0){
            baseService.deleteById("/seller/delete", $scope.ids)
                .then(function(response){
                    if (response.data){
                        $scope.reload();
                    }else{
                        alert("删除失败！");
                    }
                });
        }
    };

});