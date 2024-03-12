# if you fight for it, you deserve it.
# if you deserve it, fight for it.
def smallest_missing_positive(nums):
    n = len(nums)

    for i in range(n):
        if nums[i] <= 0 or nums[i] > n:
            nums[i] = n + 1

    for i in range(n):
        num = abs(nums[i])
        if num <= n:
            nums[num - 1] = -abs(nums[num - 1])

    for i in range(n):
        if nums[i] > 0:
            return i + 1

    return n + 1


num_test_cases = int(input())

# Process each test case
for _ in range(num_test_cases):
    n = int(input())
    arr = list(map(int, input().split()))

    result = smallest_missing_positive(arr)
    print(result)
